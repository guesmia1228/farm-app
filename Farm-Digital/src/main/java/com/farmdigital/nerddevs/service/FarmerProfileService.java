package com.farmdigital.nerddevs.service;

import com.farmdigital.nerddevs.model.FarmerProfileModel;
import com.farmdigital.nerddevs.repository.FarmerProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FarmerProfileService {
    @Autowired
    private  FarmerProfileRepository farmerProfileRepository;

    public FarmerProfileModel addFarmerProfile(FarmerProfileModel farmerProfileModel){
        return  farmerProfileRepository.save(farmerProfileModel);
    }

    public FarmerProfileModel getFarmerProfile(Long farmerId){
        return farmerProfileRepository.findAllById(farmerId);
    }
    public void updateLastSeen(Long farmerId){
        FarmerProfileModel farmerProfileModel = farmerProfileRepository.findAllById(farmerId);
        if (farmerProfileModel != null){
            farmerProfileModel.setLastSeen(LocalDateTime.now());
            farmerProfileRepository.save(farmerProfileModel);
        }
    }
}


