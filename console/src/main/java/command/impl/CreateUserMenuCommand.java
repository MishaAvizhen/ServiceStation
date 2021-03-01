package command.impl;

import command.MenuCommand;
import entity.User;
import service.UserService;
import service.dto.UserRegistrationDto;
import service.impl.UserServiceImpl;

import java.util.Scanner;

public class CreateUserMenuCommand implements MenuCommand {
    private UserService userService = UserServiceImpl.getInstance();
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
                .build();

        userService.createUser(userRegistrationDto);
        System.out.println("User" + username + " was created");

    }
}
