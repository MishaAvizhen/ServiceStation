package command.impl;

import command.MenuCommand;
import entity.RepairRequest;
import service.RepairRequestService;
import service.impl.RepairRequestServiceImpl;

import java.util.List;
import java.util.Scanner;

public class FindAllRepairRequestMenuCommand implements MenuCommand {
    private RepairRequestService repairRequestService = RepairRequestServiceImpl.getInstance();

    public FindAllRepairRequestMenuCommand() {
    }

    @Override
    public void execute() {

        Scanner scanner = new Scanner(System.in);
        List<RepairRequest> all = repairRequestService.findAllRepairRequests();
        List<RepairRequest> listOfActiveRepairRequestsOfUser = repairRequestService.getListOfAllActiveRepairRequests();
        System.out.println("Choose repair requests:  \n" +
                "1- Active  \n" + "2- All \n");
        int repairRequestsIndex = scanner.nextInt();
        if(repairRequestsIndex == 1){
            if (listOfActiveRepairRequestsOfUser.size() == 0) {
                System.out.println("Active repair request not found");
            } else {
                System.out.println(listOfActiveRepairRequestsOfUser);
            }
        } else if (repairRequestsIndex==2&&all.size()!=0) {
            System.out.println(all);
        }
    }
}
