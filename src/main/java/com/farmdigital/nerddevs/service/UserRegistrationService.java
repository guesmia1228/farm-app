package com.farmdigital.nerddevs.service;

import com.farmdigital.nerddevs.Dto.AuthenticationDto;
import com.farmdigital.nerddevs.Dto.UserRegistrationDto;
import com.farmdigital.nerddevs.model.Roles;
import com.farmdigital.nerddevs.model.UserModel;
import com.farmdigital.nerddevs.repository.RolesRepository;
import com.farmdigital.nerddevs.repository.UserRepository;
import com.farmdigital.nerddevs.security.JwtServices;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {
    private  final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private  final PasswordEncoder passwordEncoder;
private  final JwtServices jwtServices;
private  final RolesRepository rolesRepository;
    public  String   saveUer(UserRegistrationDto user){
        Roles role=rolesRepository.findByName("USER");
        System.out.println(user.getEmail());
        System.out.println(user.getPassword());
        System.out.println(user.getName());

if (        userRepository.findByEmail(user.getEmail()).isPresent()){
    return "user already exist";
}
        UserModel newUser= UserModel.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .roles(Collections.singletonList(role)).build();
   userRepository.save(newUser);
        return "user saved succesfuly";
    }


    public String  authenticateauser(AuthenticationDto req){

        authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(
                       req.getEmail(),
                       req.getPassword()
               )
        );

        UserModel user;
       if(userRepository.findByEmail(req.getEmail()).isPresent()){
           user=userRepository.findByEmail(req.getEmail()).get();
       }else{
           throw new EntityNotFoundException("invalid login credentials");
       }
//       Generate token
        return jwtServices.generateAToken(user);

    }

}
