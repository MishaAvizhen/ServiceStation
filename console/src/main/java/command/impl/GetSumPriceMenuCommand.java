package command.impl;

import command.MenuCommand;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.UserService;

import java.util.Scanner;

@Component
public class GetSumPriceMenuCommand implements MenuCommand {
    @Autowired
    private UserService userService;

    @Override
    public void execute() {
        System.out.println("Enter username:");
        Scanner scanner = new Scanner(System.in);
        String username = scanner.next();
        User userByUsername = userService.findUserByUsername(username);
        if (userByUsername != null) {
            Long id = userByUsername.getId();
            Long sumWorkPriceAndDetailPrice = userService.getSumWorkPriceAndDetailPrice(id);
            System.out.println(" Sum price for " + username + ": " + sumWorkPriceAndDetailPrice + " BYN");

        }
    }

    @Override
    public int getHandledMenuNumber() {
        return 13;
    }
}
