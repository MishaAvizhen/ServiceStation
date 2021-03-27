package handler.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import converters.impl.UserWebConverter;
import dto.UserWebDto;
import entity.User;
import handler.StoHandler;
import handler.StoHandlerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.UserService;
import service.beanUtils.ServicesBeanUtils;

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

    @Autowired
    public FindAllUsersHandler(UserService userService) {
        this.userService = userService;
    }


    @Override
    public void handleDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> allUsers = userService.findAllUsers();
        List<UserWebDto> userWebDtos = new ArrayList<>();
        UserWebConverter userWebConverter = new UserWebConverter();
        for (User user : allUsers) {
            UserWebDto userWebDto = userWebConverter.convertToDto(user);
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