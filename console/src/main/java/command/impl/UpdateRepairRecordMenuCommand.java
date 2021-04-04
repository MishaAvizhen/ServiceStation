package command.impl;

import command.MenuCommand;
import entity.RepairRecord;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.RepairRecordService;
import service.dto.RepairRecordRegistrationDto;

import java.util.Scanner;

@Component
public class UpdateRepairRecordMenuCommand implements MenuCommand {
    private static final Logger log = Logger.getLogger(UpdateRepairRecordMenuCommand.class);
    @Autowired
    private RepairRecordService repairRecordService;

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
            log.info(String.format(" Repair Record: \n  {@s} \n was update  ",recordDescriptionToUpdate.toString()));
            log.debug(String.format(" Repair Record: \n  {@s} \n was update  ",recordDescriptionToUpdate.toString()));
            System.out.println("Repair record for user " + recordDescriptionToUpdate.getRepairRequest().getUser().getUsername() + " was updated");
        } else {
            System.out.println("For username " + username + " user not found!");
        }
    }

    @Override
    public int getHandledMenuNumber() {
        return 16;
    }
}
