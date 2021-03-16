package command.impl;

import command.MenuCommand;
import entity.RepairRecord;
import service.RepairRecordService;
import service.dto.RepairRecordRegistrationDto;
import service.impl.RepairRecordServiceImpl;

import java.util.Scanner;

public class UpdateRepairRecordMenuCommand implements MenuCommand {
    private RepairRecordService repairRecordService = RepairRecordServiceImpl.getInstance();

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username: ");
        String username = scanner.next();
        System.out.println("Enter repair record description: ");
        String repairRecordDescription = scanner.next();
        RepairRecord recordDescriptionToUpdate = repairRecordService.findRepairRecordByUsernameAndRepairRecordDescription(username, repairRecordDescription);
        if (recordDescriptionToUpdate != null) {
            System.out.println("Enter repair record description to update: ");
            String repairRecordDescriptionToUpdate = scanner.next();
            System.out.println("Enter other notes to update: ");
            String otherNotes = scanner.next();
            System.out.println("Enter detail price to update: ");
            Long detailPrice = scanner.nextLong();
            System.out.println("Enter work price to update: ");
            Long workPrice = scanner.nextLong();
            RepairRecordRegistrationDto repairRecordRegistrationDto = new RepairRecordRegistrationDto.Builder()
                    .setRepairRecordDescription(repairRecordDescriptionToUpdate)
                    .setRepairRequest(recordDescriptionToUpdate.getRepairRequest().getId())
                    .setDetailPrice(detailPrice)
                    .setWorkPrice(workPrice)
                    .setOtherNotes(otherNotes)
                    .build();
            repairRecordService.updateRepairRecord(repairRecordRegistrationDto, recordDescriptionToUpdate);
            System.out.println("Repair record for user " + recordDescriptionToUpdate.getRepairRequest().getUser().getUsername() + " was updated");
        } else {
            System.out.println("For username " + username + " user not found!");
        }
    }
}
