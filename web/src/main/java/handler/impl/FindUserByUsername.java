package handler.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import converters.impl.UserWebConverter;
import dto.UserWebDto;
import entity.User;
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
public class FindUserByUsername extends StoHandlerAdapter {

    private UserService userService;


    @Autowired
    public FindUserByUsername(UserService userService) {
        this.userService = userService;

    }

    @Override
    public void handleDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserService userService = ServicesBeanUtils.getInstance().getUserService();
        String username = request.getParameter("username");
        User userByUsername = userService.findUserByUsername(username);
        UserWebConverter userWebConverter = new UserWebConverter();
        UserWebDto userWebDto = userWebConverter.convertToDto(userByUsername);

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
