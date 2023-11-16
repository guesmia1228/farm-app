package com.farmdigital.nerddevs.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("/api/v1/farm_digital/user")
public class Test {
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public  String test(@PathVariable("id") int id){
        return "hello from secure end point id :"+id;

    }
}
