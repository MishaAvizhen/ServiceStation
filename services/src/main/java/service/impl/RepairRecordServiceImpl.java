package service.impl;


import dao.RepairRecordDao;
import dao.RepairRequestDao;
import dao.impl.InMemoryRepairRecordDao;
import dao.impl.InMemoryRepairRequestDao;
import entity.RepairRecord;
import service.RepairRecordService;
import service.UserService;

import java.util.List;

public class RepairRecordServiceImpl implements RepairRecordService {
    private UserService userService = UserServiceImpl.getInstance();
    private RepairRecordDao repairRecordDao = InMemoryRepairRecordDao.getInstance();
    private RepairRequestDao repairRequestDao = InMemoryRepairRequestDao.getInstance();

    private static RepairRecordServiceImpl repairRecordService;

    private RepairRecordServiceImpl() {
    }

    public static RepairRecordServiceImpl getInstance() {
        if (repairRecordService == null) {
            repairRecordService = new RepairRecordServiceImpl();
        }
        return repairRecordService;
    }

    @Override
    public List<RepairRecord> findAllRepairRecords() {
        return repairRecordDao.findAll();
    }


    @Override
    public void deleteRepairRecordByUsernameAndRepairRecordDescription(String username, String repairRecordDescription) {
        List<RepairRecord> allRepairRecords = repairRecordService.findAllRepairRecords();
        for (RepairRecord allRepairRecord : allRepairRecords) {
            Long id = allRepairRecord.getId();
            if (allRepairRecord.getRepairRequest().getUser().getUsername().equals(username) &&
                    allRepairRecord.getRepairRecordDescription().equals(repairRecordDescription)) {
                repairRecordDao.deleteById(id);

            }
        }
    }
}
