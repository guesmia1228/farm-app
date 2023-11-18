package com.farmdigital.nerddevs.repository;

import com.farmdigital.nerddevs.model.FarmerModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FarmerRepository extends JpaRepository<FarmerModel,Integer> {

  Optional<FarmerModel> findByEmail(String  email);
  Optional<FarmerModel> findByName(String name);

}
