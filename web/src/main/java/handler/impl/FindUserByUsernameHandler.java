package handler.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import converters.impl.UserToUserWebDtoConverter;
import dto.UserWebDto;
import entity.User;
import handler.StoHandlerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class FindUserByUsernameHandler extends StoHandlerAdapter {

    private UserService userService;

    @Autowired
    public FindUserByUsernameHandler(UserService userService) {
        this.userService = userService;

    }

    @Override
    public void handleDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        User userByUsername = userService.findUserByUsername(username);
        UserToUserWebDtoConverter userToUserWebDtoConverter = new UserToUserWebDtoConverter();
        UserWebDto userWebDto = userToUserWebDtoConverter.convertToDto(userByUsername);

        PrintWriter out = response.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(userWebDto);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(jsonString);
        out.flush();
    }

    @Override
    public String getHandledURI() {

        return "/api/users/byName";
    }
}
