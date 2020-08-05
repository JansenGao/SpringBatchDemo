package com.example.batchprocessing.controller;

import com.example.batchprocessing.entity.Address;
import com.example.batchprocessing.entity.Person;
import com.example.batchprocessing.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    @Autowired
    MyService myService;

    @PostMapping("/person")
    public Person savePerson(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam(required = false) String address1,
            @RequestParam(required = false) String address2){
        return myService.savePerson(firstName, lastName, address1, address2);
    }

    @GetMapping("/person")
    public Person getPerson(
            @RequestParam Integer personId){
        return myService.getPerson(personId);
    }

    @GetMapping("/address")
    public Address getAddress(
            @RequestParam Integer addressId){
        return myService.getAddress(addressId);
    }
}
