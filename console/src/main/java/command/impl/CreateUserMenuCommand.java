package command.impl;

import command.MenuCommand;
import entity.constants.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.UserService;
import service.dto.UserRegistrationDto;

import java.util.Scanner;

@Component
public class CreateUserMenuCommand implements MenuCommand {
    @Autowired
    private UserService userService;

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
                .setPassword(password)
                .setRole(Role.USER_ROLE)
                .build();

        userService.registerUser(userRegistrationDto);
        System.out.println("User " + username + " was created");

    }

    @Override
    public int getHandledMenuNumber() {
        return 7;
    }
}
