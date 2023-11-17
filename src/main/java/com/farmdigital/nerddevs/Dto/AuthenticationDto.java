package com.farmdigital.nerddevs.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationDto {
    @NotBlank(message = "email field cannot be blank ! ")
    private String email;
    @NotBlank(message = "password field cannot be blank !")
    private String password;

}
