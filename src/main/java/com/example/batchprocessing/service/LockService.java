package com.example.batchprocessing.service;

public interface LockService {
    Boolean acquireLock(String lockName);

    Boolean releaseLock(String lockName);
}
