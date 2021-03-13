package command.impl;

import command.MenuCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.RepairRecordService;

import java.util.Scanner;

@Component
public class DeleteRepairRecordMenuCommand implements MenuCommand {
    @Autowired
    private RepairRecordService repairRecordService;

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username: ");
        String username = scanner.next();
        System.out.println("Enter repair request description: ");
        String description = scanner.next();
        repairRecordService.deleteRepairRecordByUsernameAndRepairRecordDescription(username, description);
        System.out.println(" Repair record for " + username + " was deleted");

    }

    @Override
    public int getHandledMenuNumber() {
        return 12;
    }
}
