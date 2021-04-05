package command.impl;

import command.MenuCommand;
import entity.consts.Role;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import service.UserService;
import service.dto.UserRegistrationDto;

import java.util.Scanner;

@Component
public class CreateUserMenuCommand implements MenuCommand {
    private static final Logger log = Logger.getLogger(CreateRepairRequestMenuCommand.class);
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username: ");
        String username = scanner.next();
        System.out.println("Enter phone number: ");
        String phoneNumber = scanner.next();
        System.out.println("Enter email: ");
        String email = scanner.next();
        System.out.println("Enter password: ");
        String password = scanner.next();

        UserRegistrationDto userRegistrationDto = new UserRegistrationDto.Builder()
                .setUsername(username)
                .setPhoneNumber(phoneNumber)
                .setEmail(email)
                .setPassword(passwordEncoder.encode(password))
                .setRole(Role.USER)
                .build();

        userService.registerUser(userRegistrationDto);
        log.info(String.format(" User was created "));
        System.out.println("User " + username + " was created");

    }

    @Override
    public int getHandledMenuNumber() {
        return 7;
    }
}
