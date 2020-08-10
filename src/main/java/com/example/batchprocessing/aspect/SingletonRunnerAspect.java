package com.example.batchprocessing.aspect;

import com.example.batchprocessing.annotation.SingletonRun;
import com.example.batchprocessing.exception.LockNotAcquiredException;
import com.example.batchprocessing.service.LockService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class SingletonRunnerAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(SingletonRunnerAspect.class);

    @Autowired
    LockService lockService;

    @Pointcut("@annotation(com.example.batchprocessing.annotation.SingletonRun)")
    public void singletonRun(){};

    @Around("singletonRun()")
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String className = proceedingJoinPoint.getSignature().getDeclaringTypeName();
        String methodName = proceedingJoinPoint.getSignature().getName();
        LOGGER.info("Method {}.{}() acquiring lock.", className, methodName);
        Object result;
        String lockName = getLockName(proceedingJoinPoint);

        if(lockService.acquireLock(lockName)){
            LOGGER.info("Method {}.{}() acquired lock with name {}.", className, methodName, lockName);
            result = proceedingJoinPoint.proceed();
            lockService.releaseLock(lockName);
            LOGGER.info("Method {}.{}() released lock with name {}.", className, methodName, lockName);
        }else{
            LOGGER.warn("Method {}.{}() failed to acquire lock with name {}.", className, methodName, lockName);
            throw new LockNotAcquiredException();
        }
        return result;
    }

    public String getLockName(ProceedingJoinPoint proceedingJoinPoint) throws Exception{
        String targetName = proceedingJoinPoint.getTarget().getClass().getName();
        String methodName = proceedingJoinPoint.getSignature().getName();
        Object[] arguments = proceedingJoinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        List<Method> methods = Arrays.asList(targetClass.getMethods());
        String name = methods
                .stream()
                .filter(
                        method -> method.getName().equals(methodName) &&
                        method.getParameterTypes().length == arguments.length)
                .map(
                    method -> method.getAnnotation(SingletonRun.class).name())
                .findFirst()
                .orElse("");

        return name;
    }
}
