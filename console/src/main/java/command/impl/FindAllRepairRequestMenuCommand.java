package command.impl;

import command.MenuCommand;
import entity.RepairRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.RepairRequestService;

import java.util.List;
import java.util.Scanner;

@Component
public class FindAllRepairRequestMenuCommand implements MenuCommand {
    @Autowired
    private RepairRequestService repairRequestService;

    @Override
    public void execute() {

        Scanner scanner = new Scanner(System.in);
        List<RepairRequest> all = repairRequestService.findAllRepairRequests();
        List<RepairRequest> listOfActiveRepairRequestsOfUser = repairRequestService.getListOfAllActiveRepairRequests();
        System.out.println("Choose repair requests:  \n" +
                "1- Active  \n" + "2- All \n");
        int repairRequestsIndex = scanner.nextInt();
        if (repairRequestsIndex == 1) {
            if (listOfActiveRepairRequestsOfUser.size() == 0) {
                System.out.println("Active repair request not found");
            } else {
                System.out.println(listOfActiveRepairRequestsOfUser);
            }
        } else if (repairRequestsIndex == 2 && all.size() != 0) {
            System.out.println(all);
        }
    }

    @Override
    public int getHandledMenuNumber() {
        return 2;
    }
}
