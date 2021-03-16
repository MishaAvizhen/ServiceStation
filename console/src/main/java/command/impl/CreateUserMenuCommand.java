package command.impl;

import command.MenuCommand;
import entity.consts.Role;
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
                .setRole(Role.USER_ROLE)
                .build();

        userService.registerUser(userRegistrationDto);
        System.out.println("User " + username + " was created");

    }
}
