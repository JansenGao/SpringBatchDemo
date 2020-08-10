package com.example.batchprocessing.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SingletonRunner<L extends LockService> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SingletonRunner.class);

    @Autowired
    L lockService;

    @Transactional(propagation = Propagation.REQUIRED)
    public void run(){
        if(lockService.acquireLock("")){
            LOGGER.info("Lock acquired. Start to process.");

            lockService.releaseLock("");
        }else{
            LOGGER.info("Lock NOT acquired. Quit.");
        }
    }
}
