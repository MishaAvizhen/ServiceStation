package command.impl;

import command.MenuCommand;
import entity.RepairRecord;
import service.RepairRecordService;
import service.impl.RepairRecordServiceImpl;

import java.util.List;

public class FindAllRepairRecordsMenuCommand implements MenuCommand {
    private RepairRecordService repairRecordService = RepairRecordServiceImpl.getInstance();
    public FindAllRepairRecordsMenuCommand() {
    }
    @Override
    public void execute() {
        List<RepairRecord> all = repairRecordService.findAllRepairRecords();
        if (all.size()!=0) {
            System.out.println(all);
        } else {
            System.out.println("Repair record not found");
        }
    }
}
