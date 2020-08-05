package com.example.batchprocessing.service;

public interface LockService {
    Boolean acquireLock();

    Boolean releaseLock();
}
