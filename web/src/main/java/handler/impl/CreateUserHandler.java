package handler.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import converters.impl.UserToUserWebDtoConverter;
import converters.impl.UserWebDtoToUserRegistrationDtoConverter;
import dto.UserWebDto;
import entity.User;
import entity.consts.Role;
import handler.StoHandlerAdapter;
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
public class CreateUserHandler extends StoHandlerAdapter {
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

        ObjectMapper mapper = new ObjectMapper();
        UserWebDto userWebDto = mapper.readValue(request.getInputStream(), UserWebDto.class);
        userWebDto.setRole(Role.USER);
        UserRegistrationDto userRegistrationDto = registrationDto.convertFromSourceDtoToTargetDto(userWebDto);
        User createdUser = userService.registerUser(userRegistrationDto);


        PrintWriter out = response.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(userToUserWebDtoConverter.convertToDto(createdUser));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(jsonString);
        out.flush();

    }

    @Override
    public String getHandledURI() {
        return "/api/users/createUser";
    }
}
