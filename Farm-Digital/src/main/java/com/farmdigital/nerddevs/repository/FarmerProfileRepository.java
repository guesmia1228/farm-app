package com.farmdigital.nerddevs.repository;

import com.farmdigital.nerddevs.model.FarmerProfileModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FarmerProfileRepository extends JpaRepository<FarmerProfileModel,Long> {

    FarmerProfileModel findAllById(Long farmerId);
}
