package command.impl;

import command.MenuCommand;
import entity.Appointment;
import entity.RepairRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.AppointmentService;
import service.AppointmentSlotService;
import service.RepairRequestService;
import service.dto.AppointmentSlotDto;
import service.dto.RepairRequestRegistrationDto;

import java.util.List;
import java.util.Scanner;

@Component
public class UpdateRepairRequestMenuCommand implements MenuCommand {
    private static final Logger log = Logger.getLogger(UpdateRepairRequestMenuCommand.class);
    @Autowired
    private RepairRequestService repairRequestService;

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(" Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter car remark to update: ");
        String carRemark = scanner.next();
        RepairRequest repairRequestByUsernameAndCarRemarkToUpdate = repairRequestService.findRepairRequestByUsernameAndCarRemark(username, carRemark);
        if (repairRequestByUsernameAndCarRemarkToUpdate != null) {
            System.out.println("Enter new car remark : ");
            String carRemarkToUpdate = scanner.next();
            System.out.println("Repair request description to update: ");
            String repairRequestDescriptionToUpdate = scanner.next();

            RepairRequestRegistrationDto repairRequestRegistrationDto = new RepairRequestRegistrationDto.Builder()
                    .setDateOfRequest(repairRequestByUsernameAndCarRemarkToUpdate.getDateOfRequest())
                    .setRepairRequestDescription(repairRequestDescriptionToUpdate)
                    .setRepairRequestStatus(repairRequestByUsernameAndCarRemarkToUpdate.getRepairRequestStatus())
                    .setCarRemark(carRemarkToUpdate)
                    .setUsername(repairRequestByUsernameAndCarRemarkToUpdate.getUser().getUsername())
                    .build();
            repairRequestService.updateRepairRequest(repairRequestRegistrationDto, repairRequestByUsernameAndCarRemarkToUpdate);
            log.info(String.format(" Repair request: \n  {@s} \n was update  ",repairRequestRegistrationDto.toString()));
            log.debug(String.format(" Repair request: \n  {@s} \n was update  ",repairRequestRegistrationDto.toString()));
            System.out.println("Repair request for user " + repairRequestByUsernameAndCarRemarkToUpdate.getUser().getUsername() + " was updated");
        } else {
            System.out.println("For username " + username + " user not found!");
        }

    }

    @Override
    public int getHandledMenuNumber() {
        return 15;
    }

}
