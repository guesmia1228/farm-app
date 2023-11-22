package com.farmdigital.nerddevs.service;

import com.farmdigital.nerddevs.Exceptions.InvalidAuthenticationException;
import com.farmdigital.nerddevs.model.Farmer;
import com.farmdigital.nerddevs.repository.FarmerRepository;
import com.farmdigital.nerddevs.security.JwtServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountVerificationService {
    private final JwtServices jwtServices;
    private  final FarmerRepository farmerRepository;

public String  verifyUserAccount(String  token) throws InvalidAuthenticationException{
//    ! extract the email from the token
    String  email= jwtServices.extractUsername(token);
    boolean  isAValidUser= jwtServices.CheckTokenExpiryForAccountVerification(token,email);
//    ! update the user status to be verified
    Optional<Farmer> userToUpdate= farmerRepository.findByEmail(email);
    userToUpdate.ifPresent(farmer -> farmer.setVerified(true));
    return " user account verification successful";
}
}
