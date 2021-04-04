package command.impl;

import command.MenuCommand;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.RepairRecordService;

import java.util.Scanner;

@Component
public class DeleteRepairRecordMenuCommand implements MenuCommand {
    private static final Logger log = Logger.getLogger(DeleteRepairRecordMenuCommand.class);
    @Autowired
    private RepairRecordService repairRecordService;

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username: ");
        String username = scanner.next();
        System.out.println("Enter repair request description: ");
        String description = scanner.next();
        log.info(String.format(" Repair record was deleted for {@s} ",username));
        log.debug(String.format(" Repair record was deleted for {@s} ",username));
        repairRecordService.deleteRepairRecordByUsernameAndRepairRecordDescription(username, description);
        System.out.println(" Repair record for " + username + " was deleted");

    }

    @Override
    public int getHandledMenuNumber() {
        return 12;
    }
}
