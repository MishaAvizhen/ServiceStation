package handler.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import converters.impl.UserConverterFromWebDtoToRegistrationDto;
import converters.impl.UserWebConverter;
import dto.UserWebDto;
import entity.User;
import entity.consts.Role;
import handler.StoHandlerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.UserService;
import service.beanUtils.ServicesBeanUtils;
import service.dto.UserRegistrationDto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CreateUserHandler extends StoHandlerAdapter {
    private UserService userService;

    @Autowired
    public CreateUserHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void handleDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        ObjectMapper mapper = new ObjectMapper();
        UserWebDto userWebDto = mapper.readValue(request.getInputStream(), UserWebDto.class);
        userWebDto.setRole(Role.USER);
        UserConverterFromWebDtoToRegistrationDto registrationDto = new UserConverterFromWebDtoToRegistrationDto();
        UserRegistrationDto userRegistrationDto = registrationDto.convertToUserRegistrationDto(userWebDto);
        User createdUser = userService.registerUser(userRegistrationDto);

        UserWebConverter userWebConverter = new UserWebConverter();

        PrintWriter out = response.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(userWebConverter.convertToDto(createdUser));
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
