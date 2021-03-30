package handler.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import converters.impl.UserToUserWebDtoConverter;
import dto.UserWebDto;
import entity.User;
import handler.StoHandlerAdapter;
import handler.StoRestHandler;
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
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class FindAllUsersHandler extends StoRestHandler {

    private UserService userService;
    private UserToUserWebDtoConverter userToUserWebDtoConverter;

    @Autowired
    public FindAllUsersHandler(UserService userService, UserToUserWebDtoConverter userToUserWebDtoConverter) {
        this.userService = userService;
        this.userToUserWebDtoConverter = userToUserWebDtoConverter;
    }

    @Override
    public void handleDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        List<User> allUsers = userService.findAllUsers();
        List<User> filteredUsers = allUsers.stream()
                .filter(user -> username == null || username.equals(user.getUsername()))
                .collect(Collectors.toList());
        List<UserWebDto> userWebDtos = new ArrayList<>();
        for (User user : filteredUsers) {
            UserWebDto userWebDto = userToUserWebDtoConverter.convertToDto(user);
            userWebDtos.add(userWebDto);
        }

        writeResponseAsJson(userWebDtos, response);
    }

    @Override
    public void handleDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    public String getHandledURI() {
        return "/api/users";
    }
}
