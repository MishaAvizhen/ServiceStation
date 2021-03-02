package command.impl;

import command.MenuCommand;
import entity.RepairRequest;
import entity.User;
import entity.util.RepairRequestStatus;
import service.AppointmentService;
import service.AppointmentSlotService;
import service.RepairRequestService;
import service.UserService;
import service.dto.AppointmentSlotDto;
import service.dto.RepairRequestRegistrationDto;
import service.impl.AppointmentServiceImpl;
import service.impl.AppointmentSlotServiceImpl;
import service.impl.RepairRequestServiceImpl;
import service.impl.UserServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static command.impl.CommonMethods.getDate;

public class CreateRepairRequestMenuCommand implements MenuCommand {

    private AppointmentSlotService appointmentSlotService = new AppointmentSlotServiceImpl();
    private AppointmentService appointmentService = AppointmentServiceImpl.getInstance();
    private UserService userService = UserServiceImpl.getInstance();
    private RepairRequestService repairRequestService = RepairRequestServiceImpl.getInstance();

    @Override
    public void execute() {

        Scanner scanner = new Scanner(System.in);
        System.out.println(" Enter username:");
        String username = scanner.nextLine();
        User userByUsername = userService.findUserByUsername(username);
        if (userByUsername == null) {
            System.out.println("For username " + username + " user not found!");
        }
        System.out.println("Choose date:  \n" +
                "1- Today \n" + "2- Tomorrow \n");
        int dayIndex = scanner.nextInt();
        Date date = getDate(dayIndex);

        System.out.println(" Choose desired time slot:  \n");
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
        System.out.println("Enter car remark: ");
        String carRemark = scanner.next();
        System.out.println("Repair request description: ");
        String repairRequestDescription = scanner.next();

        RepairRequestRegistrationDto repairRequestRegistrationDto = new RepairRequestRegistrationDto.Builder()
                .setDateOfRequest(date)
                .setRepairRequestDescription(repairRequestDescription)
                .setRepairRequestStatus(RepairRequestStatus.IN_PROGRESS_STATUS)
                .setCarRemark(carRemark)
                .setUsername(username)
                .build();
        repairRequestService.createRepairRequest(repairRequestRegistrationDto);

        System.out.println("Repair request for " + username + " was created");
        System.out.println("Appointment time:" + appointmentSlotDto.getStartDate()+ " - " + appointmentSlotDto.getEndDate());

    }

}
