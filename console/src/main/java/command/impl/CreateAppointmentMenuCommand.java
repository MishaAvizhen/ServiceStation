package command.impl;

import command.MenuCommand;
import entity.User;
import service.AppointmentService;
import service.AppointmentSlotService;
import service.UserService;
import service.converters.AppointmentConverterService;
import service.converters.impl.AppointmentConverterImpl;
import service.dto.AppointmentSlotDto;
import service.impl.AppointmentServiceImpl;
import service.impl.AppointmentSlotServiceImpl;
import service.impl.UserServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static command.impl.CommonMethods.getDate;

public class CreateAppointmentMenuCommand implements MenuCommand {
    private AppointmentSlotService appointmentSlotService = new AppointmentSlotServiceImpl();
    private UserService userService = UserServiceImpl.getInstance();
    private AppointmentConverterService appointmentConverterService = AppointmentConverterImpl.getInstance();

    public CreateAppointmentMenuCommand() {
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(" Enter username:  \n");
        String username = scanner.nextLine();
        User userByUsername = userService.findUserByUsername(username);
        if (userByUsername == null) {
            System.out.println("For username " + username + " user not found!");
        }

        System.out.println("Choose date:  \n" +
                "1- Today \n" + "2- Tomorrow \n");
        int dayIndex = scanner.nextInt();
        Date date = getDate(dayIndex);

        System.out.println("1- Choose time slot:  \n");
        List<AppointmentSlotDto> appointmentSlotsByDate = appointmentSlotService.getAvailableAppointmentSlotsByDate(date);
        for (int i = 0; i < appointmentSlotsByDate.size(); i++) {
            System.out.println(i + ": " + "Master: " + appointmentSlotsByDate.get(i).getMaster().getUsername()
                    + ", Start time: " + appointmentSlotsByDate.get(i).getStartDate().getHour() +
                    ", End time: " + appointmentSlotsByDate.get(i).getEndDate().getHour()
                    + "| Phone number: " + appointmentSlotsByDate.get(i).getMaster().getPhoneNumber());
        }
        int slotIndex = scanner.nextInt();
        AppointmentSlotDto appointmentSlotDto = appointmentSlotsByDate.get(slotIndex);
        appointmentConverterService.createAppointment(appointmentSlotDto, userByUsername.getId());
        System.out.println("Appointment to time:" + appointmentSlotDto.getStartDate() + " - " + appointmentSlotDto.getEndDate() +
                " was created");
    }


}
