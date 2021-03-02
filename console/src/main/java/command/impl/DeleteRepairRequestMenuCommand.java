package command.impl;

import command.MenuCommand;
import service.RepairRequestService;
import service.impl.RepairRequestServiceImpl;

import java.util.Scanner;

public class DeleteRepairRequestMenuCommand implements MenuCommand {
    private RepairRequestService repairRequestService = RepairRequestServiceImpl.getInstance();

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username: ");
        String username = scanner.next();
        System.out.println("Enter repair request description: ");
        String description = scanner.next();
        repairRequestService.deleteRepairRequestByUsernameAndRepairRequestDescription(username, description);
        System.out.println(" Repair request for " + username + " was deleted");

    }
}
