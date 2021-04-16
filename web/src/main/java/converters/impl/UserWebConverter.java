package converters.impl;

import converters.Converter;
import dto.UserWebDto;
import org.springframework.stereotype.Component;
import service.dto.UserRegistrationDto;

@Component
public class UserWebConverter implements
        Converter<UserWebDto, UserRegistrationDto> {

    @Override
    public UserRegistrationDto convertToServiceDto(UserWebDto webDto) {
        return new UserRegistrationDto.Builder()
                .setUsername(webDto.getUsername())
                .setEmail(webDto.getEmail())
                .setPhoneNumber(webDto.getPhoneNumber())
                .setPassword(webDto.getPassword())
                .setRole(webDto.getRole())
                .build();
    }

    @Override
    public UserWebDto convertToWebDto(UserRegistrationDto serviceDto) {
        throw new UnsupportedOperationException("Convertation is not supported");
    }
}


