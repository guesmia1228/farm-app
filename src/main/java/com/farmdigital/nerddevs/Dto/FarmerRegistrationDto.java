package com.farmdigital.nerddevs.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class FarmerRegistrationDto {
    @NotBlank
    private  String name;
    private  String  email=null;
    @Length(min = 6,max = 16,message = "password must contain atleast six characters")
    private  String  password;
    @NotBlank(message = "phone number is a mandatory in order to register a user")
    private  int phoneNumber;



}
