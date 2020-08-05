package com.example.batchprocessing.service;

import com.example.batchprocessing.entity.Address;
import com.example.batchprocessing.entity.Person;
import com.example.batchprocessing.repository.AddressRepository;
import com.example.batchprocessing.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;


@Service
public class MyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyService.class);

    @Autowired
    PersonRepository personRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    AddressService addressService;

    @Transactional(propagation = Propagation.REQUIRED)
    public Person savePerson(
            String firstName, String lastName, String address1, String address2){
        // Address address = saveAddress(address1, address2);

        Person person = new Person(null, firstName, lastName);
        // person.setAddress(address);

        Person savedPerson = personRepository.save(person);

        LOGGER.info("savedPerson = {}", savedPerson);

        Address address = new Address(address1, address2);
        addressService.addOne(address);

        if(new Random().nextBoolean()){
            throw new RuntimeException("Outer exception.");
        }

        return savedPerson;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Address saveAddress(
            String address1, String address2
    ){
        Address address = new Address();
        address.setAddress1(address1);
        address.setAddress2(address2);

        Address savedAddress = addressRepository.save(address);

        LOGGER.info("savedAddress = {}", savedAddress);

//        if(new Random().nextBoolean()){
//            throw new RuntimeException("Inner exception.");
//        }

        return savedAddress;
    }

    public Person getPerson(Integer personId){
        return personRepository.findById(personId).orElse(null);
    }

    public Address getAddress(Integer addressId){
        return addressRepository.findById(addressId).orElse(null);
    }
}
