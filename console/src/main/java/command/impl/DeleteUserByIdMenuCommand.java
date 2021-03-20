package command.impl;

import command.MenuCommand;
import entity.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.UserService;

import java.util.Scanner;

@Component
public class DeleteUserByIdMenuCommand implements MenuCommand {
    private static final Logger log = Logger.getLogger(DeleteUserByIdMenuCommand.class);
    @Autowired
    private UserService userService;

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
        log.info(String.format(" user {@s} was deleted  ",username));
        log.info(String.format(" user {@s} was deleted  ",username));
        System.out.println(" User " + username + " was deleted");

    }

    @Override
    public int getHandledMenuNumber() {
        return 10;
    }
}
