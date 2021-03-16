package command.impl;

import command.MenuCommand;
import entity.RepairRequest;
import service.RepairRequestService;
import service.dto.RepairRequestRegistrationDto;
import service.impl.RepairRequestServiceImpl;

import java.util.Scanner;

public class UpdateRepairRequestMenuCommand implements MenuCommand {
    private RepairRequestService repairRequestService = RepairRequestServiceImpl.getInstance();

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
            System.out.println("Repair request for user " + repairRequestByUsernameAndCarRemarkToUpdate.getUser().getUsername() + " was updated");
        } else {
            System.out.println("For username " + username + " user not found!");
        }

    }

}
