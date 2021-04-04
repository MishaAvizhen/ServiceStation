package command.impl;


import command.MenuCommand;
import entity.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.UserService;

import java.util.List;

@Component
public class FindAllUsersMenuCommand implements MenuCommand {
    private static final Logger log = Logger.getLogger(FindUserByUsernameMenuCommand.class);
    @Autowired
    private UserService userService;

    @Override
    public void execute() {
        List<User> all = userService.findAllUsers();
        if (all.size() != 0) {
            log.info(String.format(" users:\n{%s}", all.toString()));
        } else {
            log.info(String.format(" users not found"));
            log.debug(String.format(" users not found"));
        }
    }

    @Override
    public int getHandledMenuNumber() {
        return 1;
    }
}
