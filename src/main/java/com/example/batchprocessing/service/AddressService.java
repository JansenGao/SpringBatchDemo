package com.example.batchprocessing.service;

import com.example.batchprocessing.entity.Address;
import com.example.batchprocessing.repository.AddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddressService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressService.class);

    @Autowired
    AddressRepository addressRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addOne(Address address){
        Address savedAddress = addressRepository.save(address);
        LOGGER.info("savedAddress = {}", savedAddress);
    }
}
