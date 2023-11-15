package com.farmdigital.nerddevs.controller;
import com.farmdigital.nerddevs.Dto.UserRegistrationDto;
import com.farmdigital.nerddevs.service.UserRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private  final UserRegistrationService userRegistrationService;

    @PostMapping("/user")
    public ResponseEntity<?> registerUse(@RequestBody UserRegistrationDto user){
        String  res= userRegistrationService.saveUer(user);

//
return  ResponseEntity.status(HttpStatus.CREATED).body(res);
    }
}
