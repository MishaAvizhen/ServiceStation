package command.impl;

import command.MenuCommand;
import entity.RepairRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.RepairRecordService;

import java.util.List;

@Component
public class FindAllRepairRecordsMenuCommand implements MenuCommand {
    @Autowired
    private RepairRecordService repairRecordService;

    @Override
    public void execute() {
        List<RepairRecord> all = repairRecordService.findAllRepairRecords();
        if (all.size() != 0) {
            System.out.println(all);
        } else {
            System.out.println("Repair record not found");
        }
    }

    @Override
    public int getHandledMenuNumber() {
        return 3;
    }
}
