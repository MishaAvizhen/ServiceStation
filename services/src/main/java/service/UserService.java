package service;


import entity.User;
import service.dto.UserRegistrationDto;

import java.util.List;

public interface UserService {

    User findUserByUsername(String username);

    User findUserById(Long userId);

    List<User> findAllUsers();

    void deleteUserById(Long userId);

    Long getSumWorkPriceAndDetailPrice(Long userId);

    User registerUser(UserRegistrationDto userRegistrationDto);

    User updateUser(UserRegistrationDto userRegistrationDto, Long userId);


}
