package converters.impl;

import converters.ConverterFromFirstDtoToSecond;
import dto.UserWebDto;
import service.dto.UserRegistrationDto;

public class UserConverterFromWebDtoToRegistrationDto implements
        ConverterFromFirstDtoToSecond<UserRegistrationDto, UserWebDto> {

    @Override
    public UserRegistrationDto convertToEntityRegistrationDto(UserWebDto webDto) {
        return new UserRegistrationDto.Builder()
                .setUsername(webDto.getUsername())
                .setEmail(webDto.getEmail())
                .setPhoneNumber(webDto.getPhoneNumber())
                .setPassword(webDto.getPassword())
                .setRole(webDto.getRole())
                .build();
    }
}
