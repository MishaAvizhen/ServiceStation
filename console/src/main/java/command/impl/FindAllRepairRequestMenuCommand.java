package command.impl;

import command.MenuCommand;
import dao.RepairRequestDao;
import dao.impl.InMemoryRepairRequestDao;
import entity.RepairRequest;
import service.RepairRequestService;
import service.impl.RepairRequestServiceImpl;

import java.util.List;

public class FindAllRepairRequestMenuCommand implements MenuCommand {
    private RepairRequestService repairRequestService = RepairRequestServiceImpl.getInstance();

    public FindAllRepairRequestMenuCommand() {
    }

    @Override
    public void execute() {
        List<RepairRequest> all = repairRequestService.findAllRepairRequests();
        if (all.size()!=0) {
            System.out.println(all);
        } else {
            System.out.println(" Repair request not found");

        }

    }
}
