package converters.impl;

import converters.Converter;
import dto.UserWebDto;
import entity.User;

public class UserWebConverter implements Converter<User, UserWebDto> {

    @Override
    public UserWebDto convertToDto(User entity) {
        UserWebDto userWebDto = new UserWebDto();
        userWebDto.setUserId(entity.getId());
        userWebDto.setUsername(entity.getUsername());
        userWebDto.setEmail(entity.getEmail());
        userWebDto.setPhoneNumber(entity.getPhoneNumber());
        userWebDto.setPassword(entity.getPassword());
        userWebDto.setRole(entity.getRole());
        return userWebDto;
    }

    @Override
    public User convertToEntity(UserWebDto dto) {
        User user = new User();
        user.setId(dto.getUserId());
        user.setUsername(dto.getUsername());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        return user;
    }




}
