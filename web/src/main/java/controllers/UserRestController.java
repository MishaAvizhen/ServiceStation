package controllers;

import converters.impl.UserWebConverter;
import dto.UserWebDto;
import entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.UserService;
import service.dto.UserRegistrationDto;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved list"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
        @ApiResponse(code = 409, message = "The request could not be completed due to a conflict with the current state of the target resource."),
        @ApiResponse(code = 500, message = "Server ERROR. Something go wrong")
})
@Api(tags = " User controller", description = " Operations with user ")
public class UserRestController {

    private UserService userService;
    private UserWebConverter registrationDto;

    @Autowired
    public UserRestController(UserService userService, UserWebConverter registrationDto) {
        this.userService = userService;
        this.registrationDto = registrationDto;
    }

    @GetMapping
    @ApiOperation(value = "Get all users")
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/profile")
    @ApiOperation(value = "Get user by username")
    public User getUserByUsername(Principal principal) {
        String username = principal.getName();
        return userService.findUserByUsername(username);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete user")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update user")
    public User getUpdatedUser(@RequestBody UserWebDto userWebDto, @PathVariable Long id) {
        UserRegistrationDto userRegistrationDto = registrationDto.convertToServiceDto(userWebDto);
        return userService.updateUser(userRegistrationDto, id);
    }

    @PostMapping
    @ApiOperation(value = "Create user")
    public User getCreatedUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        return userService.registerUser(userRegistrationDto);
    }

    @GetMapping("/{id}/price")
    @ApiOperation(value = "Get total work and detail price of user")
    public Long getSumPriceOfUser(@PathVariable Long id) {
        return userService.getSumWorkPriceAndDetailPrice(id);
    }

    @GetMapping("/price")
    @ApiOperation(value = "Get total work and detail price of current user")
    public Long getMySumWorkAndDetailPrice(Principal principal) {
        String username = principal.getName();
        User user = userService.findUserByUsername(username);
        return userService.getSumWorkPriceAndDetailPrice(user.getId());
    }
}
