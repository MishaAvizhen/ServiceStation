package handler.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import converters.impl.UserToUserWebDtoConverter;
import converters.impl.UserWebDtoToUserRegistrationDtoConverter;
import dto.UserWebDto;
import entity.User;
import entity.consts.Role;
import handler.StoHandlerAdapter;
import handler.StoRestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.UserService;
import service.dto.UserRegistrationDto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CreateUserHandler extends StoRestHandler {
    private UserService userService;
    private UserToUserWebDtoConverter userToUserWebDtoConverter;
    private UserWebDtoToUserRegistrationDtoConverter registrationDto;

    @Autowired
    public CreateUserHandler(UserService userService, UserToUserWebDtoConverter userToUserWebDtoConverter,
                             UserWebDtoToUserRegistrationDtoConverter registrationDto) {
        this.userService = userService;
        this.userToUserWebDtoConverter = userToUserWebDtoConverter;
        this.registrationDto = registrationDto;
    }

    @Override
    public void handleDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserWebDto userWebDto = readRequestEntity(UserWebDto.class, request);
        userWebDto.setRole(Role.USER);
        UserRegistrationDto userRegistrationDto = registrationDto.convertFromSourceDtoToTargetDto(userWebDto);
        User createdUser = userService.registerUser(userRegistrationDto);

        writeResponseAsJson(userToUserWebDtoConverter.convertToDto(createdUser), response);

    }

    @Override
    public String getHandledURI() {
        return "/api/users/createUser";
    }
}
