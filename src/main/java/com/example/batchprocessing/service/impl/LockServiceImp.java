package com.example.batchprocessing.service.impl;

import com.example.batchprocessing.service.LockService;
import org.springframework.stereotype.Service;

@Service
public class LockServiceImp implements LockService {
    @Override
    public Boolean acquireLock() {
        return null;
    }

    @Override
    public Boolean releaseLock() {
        return null;
    }
}
