package com.example.batchprocessing.service.impl;

import com.example.batchprocessing.entity.Lock;
import com.example.batchprocessing.repository.LockRepository;
import com.example.batchprocessing.service.LockService;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class LockServiceDBImp implements LockService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LockServiceDBImp.class);

    @Autowired
    LockRepository lockRepository;

    @Override
    @Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.READ_COMMITTED)
    public Boolean acquireLock(String lockName) {
        Lock lock = lockRepository.findById(lockName).orElse(null);

        if(lock == null){ // New type of lock.
            lock = new Lock(lockName, "UNLOCKED", 0L);
        }

        if(lock.getLockStatus().equals("LOCKED")){
            return false;
        }

        LOGGER.info("Acquire Lock: {}", lock);

        lock.setLockStatus("LOCKED");
        lock.setLastUpdateDatetime(new Timestamp(new Date().getTime()));

        try{
            Lock updLock = lockRepository.save(lock);
            LOGGER.info("[Lock] Updated Lock : {}", updLock);
        }catch(Exception exception){
            LOGGER.info("Failed to acquire lock.");
            return false;
        }

        return true;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Boolean releaseLock(String lockName) {
        Lock lock = lockRepository.findById(lockName).orElseGet(null);
        if(lock == null){
            return false;
        }else{
            LOGGER.info("Release Lock: {}", lock);
            lock.setLockStatus("UNLOCKED");
            lock.setLastUpdateDatetime(new Timestamp(new Date().getTime()));
            Lock updLock = lockRepository.save(lock);
            LOGGER.info("[Release] Updated lock : {}", updLock);
            return true;
        }
    }
}
