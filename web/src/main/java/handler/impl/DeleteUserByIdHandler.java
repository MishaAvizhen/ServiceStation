package handler.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
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

@Component
public class DeleteUserByIdHandler extends StoHandlerAdapter {
    private UserService userService;

    @Autowired
    public DeleteUserByIdHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void handleDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = ServicesBeanUtils.getInstance().getUserService();
        Long userId = Long.valueOf(request.getParameter("userId"));
        userService.deleteUserById(userId);

        PrintWriter out = response.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString("User with id: " + userId + " was delete");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(jsonString);
        out.flush();

    }

    @Override
    public String getHandledURI() {
        return "/api/users/deleteById";
    }
}
