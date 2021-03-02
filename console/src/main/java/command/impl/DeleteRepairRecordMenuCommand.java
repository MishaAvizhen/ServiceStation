package command.impl;

import command.MenuCommand;
import service.RepairRecordService;
import service.impl.RepairRecordServiceImpl;

import java.util.Scanner;

public class DeleteRepairRecordMenuCommand implements MenuCommand {
    private RepairRecordService repairRecordService = RepairRecordServiceImpl.getInstance();

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
}
