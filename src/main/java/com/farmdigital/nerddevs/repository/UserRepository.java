package com.farmdigital.nerddevs.repository;

import com.farmdigital.nerddevs.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel,Integer> {

  Optional<UserModel> findByEmail(String  email);
  Optional<UserModel> findByName(String name);

}
