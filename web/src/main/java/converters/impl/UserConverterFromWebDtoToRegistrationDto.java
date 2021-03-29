package converters.impl;

import converters.ConverterFromFirstDtoToSecond;
import dto.UserWebDto;
import org.springframework.stereotype.Component;
import service.dto.UserRegistrationDto;

@Component
public class UserConverterFromWebDtoToRegistrationDto implements
        ConverterFromFirstDtoToSecond<UserWebDto, UserRegistrationDto> {

    @Override
    public UserRegistrationDto convertFromSourceDtoToTargetDto(UserWebDto webDto) {
        return new UserRegistrationDto.Builder()
                .setUsername(webDto.getUsername())
                .setEmail(webDto.getEmail())
                .setPhoneNumber(webDto.getPhoneNumber())
                .setPassword(webDto.getPassword())
                .setRole(webDto.getRole())
                .build();
    }
}
