package command.impl;

import command.MenuCommand;
import entity.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.AppointmentService;
import service.AppointmentSlotService;
import service.UserService;
import service.dto.AppointmentSlotDto;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static command.impl.CommonMethods.getDate;

@Component
public class CreateAppointmentMenuCommand implements MenuCommand {
    private static final Logger log = Logger.getLogger(CreateAppointmentMenuCommand.class);
    @Autowired
    private AppointmentSlotService appointmentSlotService;
    @Autowired
    private UserService userService;
    @Autowired
    private AppointmentService appointmentService;

    public CreateAppointmentMenuCommand() {
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(" Enter username:");
        String username = scanner.nextLine();
        User userByUsername = userService.findUserByUsername(username);
        if (userByUsername == null) {
            log.info(String.format(" users {%s} not found", username));
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
        appointmentService.createAppointment(appointmentSlotDto, userByUsername.getId());
        log.info(String.format(" Appointment was created "));
        log.debug(String.format("Appointment was created {%s}", appointmentSlotDto.toString()));
        System.out.println("Appointment to time:" + appointmentSlotDto.getStartDate() + " - " + appointmentSlotDto.getEndDate() +
                " was created");
    }

    @Override
    public int getHandledMenuNumber() {
        return 5;
    }


}
