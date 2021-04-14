package handler.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import handler.StoHandlerAdapter;
import handler.StoRestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.UserService;
import service.beanUtils.ServicesBeanUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class DeleteUserByIdHandler extends StoRestHandler{
    private UserService userService;

    @Autowired
    public DeleteUserByIdHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void handleDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long userId = Long.valueOf(request.getParameter("userId"));
        userService.deleteUserById(userId);

        writeResponseAsJson("User with id: " + userId + " was delete", response);
    }

    @Override
    public String getHandledURI() {
        return "/api/users/deleteById";
    }
}
