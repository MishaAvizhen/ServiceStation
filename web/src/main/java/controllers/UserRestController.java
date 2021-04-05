package controllers;

import converters.impl.UserWebDtoToUserRegistrationDtoConverter;
import dto.UserWebDto;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import service.UserService;
import service.dto.UserRegistrationDto;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class UserRestController {
    private UserService userService;
    private UserWebDtoToUserRegistrationDtoConverter registrationDto;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserRestController(UserService userService, UserWebDtoToUserRegistrationDtoConverter registrationDto,
                              PasswordEncoder passwordEncoder) {
        this.userService = userService;

        this.registrationDto = registrationDto;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/users/{userId}")
    public User getUserById(@PathVariable Long userId) {
        return userService.findUserById(userId);
    }

    @GetMapping("/users/username/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userService.findUserByUsername(username);

    }

    @GetMapping("/users/delete/{userId}")
    public void deleteUserById(@PathVariable Long userId) {
        userService.deleteUserById(userId);

    }

    @PostMapping("/users/{userId}/update")
    public User getUpdatedUser(@RequestBody UserWebDto userWebDto, @PathVariable Long userId) {
        User userToUpdate = userService.findUserById(userId);
        if (userToUpdate == null) {
            throw new UnsupportedOperationException("User with id " + userWebDto.getUserId() + " not found");

        } else {
            UserRegistrationDto userRegistrationDto = registrationDto.convertFromSourceDtoToTargetDto(userWebDto);
            return userService.updateUser(userRegistrationDto, userToUpdate);
        }
    }

    @PostMapping("/users/create")
    public User getCreatedUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        userRegistrationDto.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        return userService.registerUser(userRegistrationDto);
    }

    @GetMapping("/users/{userId}/price")
    public Long getSumPriceOfUser(@PathVariable Long userId) {
        return userService.getSumWorkPriceAndDetailPrice(userId);
    }

}
