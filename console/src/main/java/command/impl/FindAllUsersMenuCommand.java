package command.impl;


import command.MenuCommand;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.UserService;

import java.util.List;

@Component
public class FindAllUsersMenuCommand implements MenuCommand {
    @Autowired
    private UserService userService;

    @Override
    public void execute() {
        List<User> all = userService.findAllUsers();
        if (all.size() != 0) {
            System.out.println(all);
        } else {
            System.out.println("User not found");
        }
    }

    @Override
    public int getHandledMenuNumber() {
        return 1;
    }
}
