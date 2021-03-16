package command.impl;

import command.MenuCommand;
import entity.User;
import service.UserService;
import service.impl.UserServiceImpl;

import java.util.Scanner;

public class DeleteUserByIdMenuCommand implements MenuCommand {
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username: ");
        String username = scanner.next();
        User userByUsername = userService.findUserByUsername(username);
        if (userByUsername == null) {
            System.out.println(" User" + username + " not found");
        }
        Long id = userByUsername.getId();
        userService.deleteUserById(id);
        System.out.println(" User " + username + " was deleted");

    }
}
