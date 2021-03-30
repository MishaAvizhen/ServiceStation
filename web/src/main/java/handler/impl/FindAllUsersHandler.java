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
import java.util.ArrayList;
import java.util.List;

@Component
public class FindAllUsersHandler extends StoHandlerAdapter {

    private UserService userService;
    private UserToUserWebDtoConverter userToUserWebDtoConverter;

    @Autowired
    public FindAllUsersHandler(UserService userService, UserToUserWebDtoConverter userToUserWebDtoConverter) {
        this.userService = userService;
        this.userToUserWebDtoConverter = userToUserWebDtoConverter;
    }






    @Override
    public void handleDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> allUsers = userService.findAllUsers();
        List<UserWebDto> userWebDtos = new ArrayList<>();
        for (User user : allUsers) {
            UserWebDto userWebDto = userToUserWebDtoConverter.convertToDto(user);
            userWebDtos.add(userWebDto);
        }

        PrintWriter out = response.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(userWebDtos);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(jsonString);
        out.flush();
    }

    @Override
    public void handleDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    public String getHandledURI() {
        return "/api/users";
    }
}
