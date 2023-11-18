package com.farmdigital.nerddevs.controller;

import com.farmdigital.nerddevs.model.FarmerProfileModel;
import com.farmdigital.nerddevs.repository.FarmerProfileRepository;
import com.farmdigital.nerddevs.service.FarmerProfileService;
import io.jsonwebtoken.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Controller
@RequestMapping( )
public class FarmerProfileController {
    @Autowired
    FarmerProfileService farmerProfileService;

    @PostMapping()
    public ResponseEntity<FarmerProfileModel> createFarmerProfile(
            @RequestParam("name")String userName,
            @RequestParam("address")String address,
            @RequestParam("crpType")String cropType,
            @RequestParam("image")MultipartFile image) throws IOException {


        FarmerProfileModel farmer = new FarmerProfileModel();
        farmer.setAddress(address);
        farmer.setCropType(cropType);
        farmer.setUserName(userName);
        //farmer.setImgUrl(imgUrl);


        FarmerProfileModel savedFarmer = farmerProfileService.addFarmerProfile(farmer);
        return new ResponseEntity<>(savedFarmer, HttpStatus.CREATED);



    }
    @GetMapping("/{farmerId}")
    public ResponseEntity<FarmerProfileModel> getFarmerProfile(@PathVariable Long farmerId){
        FarmerProfileModel farmer = new FarmerProfileService().getFarmerProfile(farmerId);
        if (farmer != null){
            return new ResponseEntity<>(farmer, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(farmer,HttpStatus.NOT_FOUND);
        }
    }


}
