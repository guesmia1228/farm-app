package com.farmdigital.nerddevs.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor

public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;

    private  String name;
    private  String  email;
    private  String  password;


}
