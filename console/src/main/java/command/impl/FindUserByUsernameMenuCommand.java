package command.impl;

import command.MenuCommand;
import entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.UserService;
import ui.menu.ConsoleMenu;

import java.util.Scanner;

@Component
public class FindUserByUsernameMenuCommand implements MenuCommand {
    private static final Logger log = Logger.getLogger(FindUserByUsernameMenuCommand.class);

    @Autowired
    private UserService userService;

    @Override
    public void execute() {
        System.out.println("Enter username:");
        Scanner scanner = new Scanner(System.in);
        String username = scanner.next();
        log.info(String.format("Find user by name: {%s}", username));
        log.debug(String.format("Find user by name: {%s}", username));
        User userByUsername = userService.findUserByUsername(username);
        if (userByUsername != null) {
            log.info(String.format("Detected User: {%s}", userByUsername));
        } else {
            log.info(String.format("user {%s} not found", username));
            log.debug(String.format("user {%s} not found", username));
        }
    }

    @Override
    public int getHandledMenuNumber() {
        return 4;
    }
}
