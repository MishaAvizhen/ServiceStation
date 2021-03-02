package command.impl;

import command.MenuCommand;
import entity.RepairRequest;
import entity.User;
import entity.util.RepairRequestStatus;
import service.RepairRequestService;
import service.UserService;
import service.dto.RepairRequestRegistrationDto;
import service.impl.RepairRequestServiceImpl;
import service.impl.UserServiceImpl;

import java.util.Date;
import java.util.Scanner;

import static command.impl.CommonMethods.getDate;

public class CreateRepairRequestMenuCommand implements MenuCommand {

    private RepairRequestService repairRequestService = RepairRequestServiceImpl.getInstance();

    @Override
    public void execute() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose date:  \n" +
                "1- Today \n" + "2- Tomorrow \n");
        int dayIndex = scanner.nextInt();
        Date date = getDate(dayIndex);
        System.out.println("Enter car remark: ");
        String carRemark = scanner.next();
        System.out.println("Repair request description: ");
        String repairRequestDescription = scanner.next();
        System.out.println("Enter username: ");
        String username = scanner.next();
        RepairRequestRegistrationDto repairRequestRegistrationDto = new RepairRequestRegistrationDto.Builder()
                .setDateOfRequest(date)
                .setRepairRequestDescription(repairRequestDescription)
                .setRepairRequestStatus(RepairRequestStatus.IN_PROGRESS_STATUS)
                .setCarRemark(carRemark)
                .setUsername(username)
                .build();
        repairRequestService.createRepairRequest(repairRequestRegistrationDto);
        System.out.println("Repair request for " + username + " was created");

    }

}

