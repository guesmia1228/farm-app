package com.farmdigital.nerddevs.service;

import com.farmdigital.nerddevs.Dto.UserRegistrationDto;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {

    public  String   saveUer(UserRegistrationDto user){
        System.out.println(user.getEmail());
        System.out.println(user.getPassword());
        System.out.println(user.getName());
        return "user saved succesfuly";


    }
}
