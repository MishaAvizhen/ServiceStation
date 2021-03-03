package service.converters;

import service.dto.UserRegistrationDto;

public interface UserConverterService {
    void createUser(UserRegistrationDto userRegistrationDto);
}
