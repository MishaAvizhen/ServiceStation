package command.impl;

import command.MenuCommand;
import entity.User;
import service.UserService;
import service.impl.UserServiceImpl;

import java.util.Scanner;

public class GetSumPriceMenuCommand implements MenuCommand {
    private UserService userService = UserServiceImpl.getInstance();

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
}
