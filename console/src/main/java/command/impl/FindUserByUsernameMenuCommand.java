package command.impl;

import command.MenuCommand;
import entity.User;
import service.UserService;
import service.impl.UserServiceImpl;

import java.util.Scanner;

public class FindUserByUsernameMenuCommand implements MenuCommand {
    private UserService userService = UserServiceImpl.getInstance();

    public FindUserByUsernameMenuCommand() {
    }

    @Override
    public void execute() {
        System.out.println("Enter username:");
        Scanner scanner = new Scanner(System.in);
        String username = scanner.next();
        User userByUsername = userService.findUserByUsername(username);
        if (userByUsername != null) {
            System.out.println("Detected User: " + userByUsername);
        } else {
            System.out.println("For username " + username + " user not found!");
        }
    }
}
