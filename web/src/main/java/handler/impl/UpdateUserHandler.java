package handler.impl;

import converters.impl.UserWebDtoToUserRegistrationDtoConverter;
import dto.UserWebDto;
import entity.User;
import handler.StoRestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.UserService;
import service.dto.UserRegistrationDto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UpdateUserHandler extends StoRestHandler {
    private UserService userService;
    private UserWebDtoToUserRegistrationDtoConverter registrationDto;

    @Autowired
    public UpdateUserHandler(UserService userService, UserWebDtoToUserRegistrationDtoConverter registrationDto) {
        this.userService = userService;
        this.registrationDto = registrationDto;
    }

    @Override
    public void handleDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserWebDto userWebDto = readRequestEntity(UserWebDto.class, request);

        Long userId = userWebDto.getUserId();
        User userById = userService.findUserById(userId);
        if (userById != null) {
            UserRegistrationDto userRegistrationDto = registrationDto.convertFromSourceDtoToTargetDto(userWebDto);
            User userAfterUpdate = userService.updateUser(userRegistrationDto, userById);

            writeResponseAsJson(userAfterUpdate, response);

        } else {
            throw new UnsupportedOperationException("User with id " + userWebDto.getUserId() + " not found");
        }

    }

    @Override
    public String getHandledURI() {
        return "/api/users/updateUser";
    }
}
