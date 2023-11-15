package com.farmdigital.nerddevs.service;

import com.farmdigital.nerddevs.Dto.UserRegistrationDto;
import com.farmdigital.nerddevs.model.UserModel;
import com.farmdigital.nerddevs.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {
    private  final UserRepository userRepository;

    public  String   saveUer(UserRegistrationDto user){
        System.out.println(user.getEmail());
        System.out.println(user.getPassword());
        System.out.println(user.getName());

if (        userRepository.findByEmail(user.getEmail()).isPresent()){
    return "user already exist";
}
        UserModel newUser= UserModel.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword()).build();
   userRepository.save(newUser);
        return "user saved succesfuly";
    }
}
