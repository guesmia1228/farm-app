package com.farmdigital.nerddevs.controller;

import com.farmdigital.nerddevs.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/farm_digital/super")
public class Test {
private  final UserRepository userRepository;
    public Map<String ,String > userProfile(){
        Map<String ,String > profile= new HashMap<>();
        profile.put("name","bob");
        profile.put("status","active");
profile.put("intrest","fishing");

        return profile;

    }
    @GetMapping()

    public  ResponseEntity<?> test(@RequestParam("userId") String email){
        ;

        URI uri= URI.create("/profile");

        return ResponseEntity.created(uri).body("user with id "+email +"was created");

    }




}
