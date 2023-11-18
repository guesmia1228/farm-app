package com.farmdigital.nerddevs.service;

import com.farmdigital.nerddevs.Dto.AuthenticationDto;
import com.farmdigital.nerddevs.Dto.FarmerRegistrationDto;
import com.farmdigital.nerddevs.Exceptions.UserAlreadyExistException;
import com.farmdigital.nerddevs.model.Roles;
import com.farmdigital.nerddevs.model.FarmerModel;
import com.farmdigital.nerddevs.repository.RolesRepository;
import com.farmdigital.nerddevs.repository.FarmerRepository;
import com.farmdigital.nerddevs.security.JwtServices;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {
    private final FarmerRepository farmerRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtServices jwtServices;
    private final RolesRepository rolesRepository;
    private Map<String, String> response = new HashMap<>();

    public Map<String, String> saveUer(FarmerRegistrationDto user) throws Exception {

        Roles role = rolesRepository.findByName("USER");
        System.out.println(user.getEmail());
        System.out.println(user.getPassword());
        System.out.println(user.getName());

//        ! if the farmer already exist throw an exception
        if (farmerRepository.findByEmail(user.getEmail()).isPresent()) {

            throw new UserAlreadyExistException("user alredy exist !, please try to log in");
        }
//      !  create a new user
        FarmerModel newUser = FarmerModel.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .farmerId(createUniqueId(user.getPhoneNumber()))
                .roles(Collections.singletonList(role))
                .phoneNumber(user.getPhoneNumber())
                .registrationTime(timeCreatedAccout())
                .build();
        farmerRepository.save(newUser);
        response.put("message","user created successfully");
        return  response;

    }

    private String timeCreatedAccout() {
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("MM/dd/yyy 'at' hh:mm a");
        return formatter.format(LocalDateTime.now());
    }

    //    ! method that helps us create a unique id for the user
    private String createUniqueId(int phoneNumber) {
        String  num= String.valueOf(phoneNumber);
        String uniqueId = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddss"));
        return "FARMER-"+ num.substring(7) +uniqueId;
    }

//    ! method to check the time when the user created an account



    public String authenticateauser(AuthenticationDto req) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getEmail(),
                        req.getPassword()
                )
        );

        FarmerModel user;
        if (farmerRepository.findByEmail(req.getEmail()).isPresent()) {
            user = farmerRepository.findByEmail(req.getEmail()).get();
        } else {
            throw new EntityNotFoundException("invalid login credentials");
        }
//       Generate token
        return jwtServices.generateAToken(user);

    }

}
