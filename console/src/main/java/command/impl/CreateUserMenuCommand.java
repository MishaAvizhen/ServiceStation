package command.impl;

import command.MenuCommand;
import service.converters.ConverterEntityDtoService;
import service.converters.impl.ConverterEntityDtoImpl;
import service.dto.UserRegistrationDto;

import java.util.Scanner;

public class CreateUserMenuCommand implements MenuCommand {
    private ConverterEntityDtoService converterEntityDto = ConverterEntityDtoImpl.getInstance();

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

        converterEntityDto.createUser(userRegistrationDto);
        System.out.println("User " + username + " was created");

    }
}
