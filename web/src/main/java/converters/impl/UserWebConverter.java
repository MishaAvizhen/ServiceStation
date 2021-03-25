package converters.impl;

import converters.Converter;
import dto.UserWebDto;
import entity.User;

public class UserWebConverter implements Converter<User, UserWebDto> {

    @Override
    public UserWebDto convertToDto(User entity) {
        UserWebDto userWebDto = new UserWebDto();
        userWebDto.setUsername(entity.getUsername());
        userWebDto.setEmail(entity.getEmail());
        userWebDto.setPhoneNumber(entity.getPhoneNumber());
        userWebDto.setPassword(entity.getPassword());
        userWebDto.setRole(entity.getRole());
        return userWebDto;
    }
}
