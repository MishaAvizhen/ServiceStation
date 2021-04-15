package service.converters.impl;

import entity.User;
import org.springframework.stereotype.Component;
import service.converters.Converter;
import service.dto.UserRegistrationDto;

@Component
public class UserConverter implements Converter<User, UserRegistrationDto> {
    public UserConverter() {
    }

    @Override
    public User convertToEntity(UserRegistrationDto dto) {
        User user = new User();
        return convertToExistingEntity(dto, user);
    }

    @Override
    public User convertToExistingEntity(UserRegistrationDto dto, User entity) {
        entity.setUsername(dto.getUsername());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setEmail(dto.getEmail());
        entity.setRole(dto.getRole());
        entity.setPassword(dto.getPassword());
        return entity;
    }

    @Override
    public UserRegistrationDto convertToDto(User entity) {
        return new UserRegistrationDto.Builder()
                .setUsername(entity.getUsername())
                .setPhoneNumber(entity.getPhoneNumber())
                .setEmail(entity.getEmail())
                .setPassword(entity.getPassword())
                .setRole(entity.getRole())
                .build();
    }
}
