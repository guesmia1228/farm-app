package com.farmdigital.nerddevs.repository;

import com.farmdigital.nerddevs.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserModel,Integer> {

    @Query("select * from  UserModel where name=?")
    UserModel findUserByname()
}
