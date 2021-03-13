package command.impl;

import command.MenuCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.RepairRequestService;

import java.util.Scanner;

@Component
public class DeleteRepairRequestMenuCommand implements MenuCommand {
    @Autowired
    private RepairRequestService repairRequestService;

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

    @Override
    public int getHandledMenuNumber() {
        return 11;
    }
}
