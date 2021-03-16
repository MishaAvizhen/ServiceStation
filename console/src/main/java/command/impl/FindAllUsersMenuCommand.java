package command.impl;


import command.MenuCommand;
import entity.User;
import service.UserService;
import service.impl.UserServiceImpl;

import java.util.List;

public class FindAllUsersMenuCommand implements MenuCommand {
    private UserService userService = UserServiceImpl.getInstance();

    public FindAllUsersMenuCommand() {
    }

    @Override
    public void execute() {
        List<User> all = userService.findAllUsers();
        if (all.size() != 0) {
            System.out.println(all);
        } else {
            System.out.println("User not found");
        }
    }
}
