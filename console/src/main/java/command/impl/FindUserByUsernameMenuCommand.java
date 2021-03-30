package command.impl;

import command.MenuCommand;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.UserService;

import java.util.Scanner;

@Component
public class FindUserByUsernameMenuCommand implements MenuCommand {
    @Autowired
    private UserService userService;

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

    @Override
    public int getHandledMenuNumber() {
        return 4;
    }
}
