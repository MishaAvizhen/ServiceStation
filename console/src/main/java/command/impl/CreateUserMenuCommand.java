package command.impl;

import command.MenuCommand;
import entity.constants.Role;
import service.converters.UserConverterService;
import service.converters.impl.UserConverterImpl;
import service.dto.UserRegistrationDto;

import java.util.Scanner;

public class CreateUserMenuCommand implements MenuCommand {
    private UserConverterService userConverterService = UserConverterImpl.getInstance();

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
                .setRole(Role.ADMIN_ROLE)
                .build();

        userConverterService.createUser(userRegistrationDto);
        System.out.println("User " + username + " was created");

    }
}
