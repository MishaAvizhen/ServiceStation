package controllers;

import converters.impl.UserWebDtoToUserRegistrationDtoConverter;
import dto.UserWebDto;
import entity.User;
import exceptions.NotContentException;
import exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import service.UserService;
import service.dto.UserRegistrationDto;

import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
public class UserRestController {
    private UserService userService;
    private UserWebDtoToUserRegistrationDtoConverter registrationDto;

    @Autowired
    public UserRestController(UserService userService, UserWebDtoToUserRegistrationDtoConverter registrationDto) {
        this.userService = userService;

        this.registrationDto = registrationDto;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Long userId) {
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new ResourceNotFoundException(userId.toString());
        }
        return user;
    }

    @GetMapping("/username/{username}")
    public User getUserByUsername(@PathVariable String username) {
        User user = userService.findUserByUsername(username);
        if (user == null) {
            throw new ResourceNotFoundException(username);
        }
        return user;
    }


    @DeleteMapping ("/{userId}")
    public void deleteUserById(@PathVariable Long userId) {
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new NotContentException(userId.toString());
        } else {
            userService.deleteUserById(userId);
        }
    }

    @PutMapping("/{userId}")
    public User getUpdatedUser(@RequestBody UserWebDto userWebDto, @PathVariable Long userId) {
        User userToUpdate = userService.findUserById(userId);
        if (userToUpdate == null) {
            throw new ResourceNotFoundException("User with id " + userWebDto.getUserId() + " not found");
        } else {

            UserRegistrationDto userRegistrationDto = registrationDto.convertFromSourceDtoToTargetDto(userWebDto);

            return userService.updateUser(userRegistrationDto, userToUpdate);
        }
    }

    @PostMapping("/create")
    public User getCreatedUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        return userService.registerUser(userRegistrationDto);
    }

    @GetMapping("/{userId}/price")
    public Long getSumPriceOfUser(@PathVariable Long userId) {
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new ResourceNotFoundException(userId.toString());
        }
        return userService.getSumWorkPriceAndDetailPrice(userId);
    }

}
