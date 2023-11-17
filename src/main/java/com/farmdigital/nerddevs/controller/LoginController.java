package com.farmdigital.nerddevs.controller;
import com.farmdigital.nerddevs.Dto.AuthenticationDto;
import com.farmdigital.nerddevs.Dto.UserRegistrationDto;
import com.farmdigital.nerddevs.service.UserRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor

@RequestMapping("/super")
public class LoginController {

    private  final UserRegistrationService userRegistrationService;

//    ! add user route
    @PostMapping("/user/register")
    public ResponseEntity<?> registerUse(@RequestBody UserRegistrationDto user){
        String  res= userRegistrationService.saveUer(user);

//
    return  ResponseEntity.status(HttpStatus.CREATED).body(res);
    }
    @PostMapping("/user/authenticate")

    public  ResponseEntity<?> authenticateUser(@RequestBody AuthenticationDto request){
var res=userRegistrationService.authenticateauser(request);
        return  ResponseEntity.status(HttpStatus.OK).body(res);
    }

// todo  authenticate user route




}
