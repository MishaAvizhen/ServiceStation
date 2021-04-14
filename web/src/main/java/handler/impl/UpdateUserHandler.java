package handler.impl;

import converters.impl.UserWebDtoToUserRegistrationDtoConverter;
import dto.UserWebDto;
import dto.errorHandling.ErrorDto;
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
import java.util.HashMap;
import java.util.Map;

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

        try {
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
        } catch (UnsupportedOperationException e) {
            ErrorDto errorDto = new ErrorDto(e.getMessage(), "admin@mail.ru");
            writeErrorResponseAsJson(errorDto, response, HttpServletResponse.SC_NOT_FOUND);
        } catch (RuntimeException e) {
            ErrorDto errorDto = new ErrorDto(e.getMessage(), "admin@mail.ru");
            writeErrorResponseAsJson(errorDto, response, HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            ErrorDto errorDto = new ErrorDto(e.getMessage(), "admin@mail.ru");
            writeErrorResponseAsJson(errorDto, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String getHandledURI() {
        return "/api/users/updateUser";
    }
}
