package handler.impl;

import entity.User;
import handler.StoRestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class GetSumRepairRecordPriceOfByUserHandler extends StoRestHandler {

    private UserService userService;

    @Autowired
    public GetSumRepairRecordPriceOfByUserHandler(UserService userService) {
        this.userService = userService;

    }

    @Override
    public void handleDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long userId = Long.valueOf(request.getParameter("userId"));
        User userById = userService.findUserById(userId);

        Long sumWorkPriceAndDetailPrice = userService.getSumWorkPriceAndDetailPrice(userId);
        writeResponseAsJson("Sum price for " + userById.getUsername() + ": " + sumWorkPriceAndDetailPrice + " BYN", response);

    }

    @Override
    public String getHandledURI() {
        return "/api/users/price";
    }
}
