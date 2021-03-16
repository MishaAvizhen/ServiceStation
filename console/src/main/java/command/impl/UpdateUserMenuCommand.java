package command.impl;

import command.MenuCommand;
import entity.User;
import service.UserService;
import service.dto.UserRegistrationDto;
import service.impl.UserServiceImpl;

import java.util.Scanner;

public class UpdateUserMenuCommand implements MenuCommand {
    private UserService userService = UserServiceImpl.getInstance();
    @Override
    public void execute() {
        System.out.println("Enter username:");
        Scanner scanner = new Scanner(System.in);
        String username = scanner.next();
        User userToUpdate = userService.findUserByUsername(username);
        if (userToUpdate != null) {
            System.out.println("Enter username to update: ");
            String updateUsername = scanner.next();
            System.out.println("Enter phone number to update: ");
            String phoneNumber = scanner.next();
            System.out.println("Enter email to update: ");
            String email = scanner.next();
            System.out.println("Enter password to update: ");
            String password = scanner.next();

            UserRegistrationDto userRegistrationDto = new UserRegistrationDto.Builder()
                    .setUsername(updateUsername)
                    .setPhoneNumber(phoneNumber)
                    .setEmail(email)
                    .setPassword(password)
                    .setRole(userToUpdate.getRole())
                    .build();
            userService.updateUser(userRegistrationDto, userToUpdate);
            System.out.println("User " + updateUsername + " was updated");
        } else {
            System.out.println("For username " + username + " user not found!");
        }

    }
}
