package com.farmdigital.nerddevs.Interfaces;

import com.farmdigital.nerddevs.Dto.FarmerRegistrationDto;
import com.farmdigital.nerddevs.Dto.ResponseMessageDto;

public interface FarmerLoginInterface {
    ResponseMessageDto saveNewUser(FarmerRegistrationDto registrationDto);

}
