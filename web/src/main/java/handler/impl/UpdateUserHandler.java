package handler.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import converters.impl.UserWebDtoToUserRegistrationDtoConverter;
import dto.UserWebDto;
import entity.User;
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
public class UpdateUserHandler extends StoHandlerAdapter {
    private UserService userService;

    @Autowired
    public UpdateUserHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void handleDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        UserWebDto userWebDto = mapper.readValue(request.getInputStream(), UserWebDto.class);

        UserWebDtoToUserRegistrationDtoConverter registrationDto = new UserWebDtoToUserRegistrationDtoConverter();
        Long userId = userWebDto.getUserId();
        User userById = userService.findUserById(userId);
        if (userById != null) {
            UserRegistrationDto userRegistrationDto = registrationDto.convertFromSourceDtoToTargetDto(userWebDto);
            User userAfterUpdate = userService.updateUser(userRegistrationDto, userById);
            PrintWriter out = response.getWriter();
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(userAfterUpdate);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(jsonString);
            out.flush();

        } else {
            throw new UnsupportedOperationException("User with id " + userWebDto.getUserId() + " not found");
        }

    }

    @Override
    public String getHandledURI() {
        return "/api/users/updateUser";
    }
}
