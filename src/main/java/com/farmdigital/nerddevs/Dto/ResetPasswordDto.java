package com.farmdigital.nerddevs.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResetPasswordDto {
    @NotBlank(message = "email cannot be blank")
    @Email( message = "a valid email address is required ")
    private  String  email;

}
