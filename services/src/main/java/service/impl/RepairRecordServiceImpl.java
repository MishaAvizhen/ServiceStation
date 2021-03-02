package service.impl;


import dao.RepairRecordDao;
import dao.RepairRequestDao;
import dao.impl.InMemoryRepairRecordDao;
import dao.impl.InMemoryRepairRequestDao;
import entity.RepairRecord;
import entity.RepairRequest;
import entity.util.RepairRequestStatus;
import service.RepairRecordService;
import service.UserService;
import service.dto.RepairRecordRegistrationDto;

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
    public void createRepairRecord(RepairRecordRegistrationDto repairRecordRegistrationDto) {
        RepairRequest repairRequestDaoById = repairRequestDao.findById(repairRecordRegistrationDto.getRepairRequestId());
        RepairRecord repairRecord = new RepairRecord();
        repairRecord.setRepairRequest(repairRequestDaoById);
        repairRecord.setOtherNotes(repairRecordRegistrationDto.getOtherNotes());
        repairRecord.setRepairRecordDescription(repairRecordRegistrationDto.getRepairRecordDescription());
        repairRecord.setDetailPrice(repairRecordRegistrationDto.getDetailPrice());
        repairRecord.setWorkPrice(repairRecordRegistrationDto.getWorkPrice());
        repairRequestDaoById.setRepairRequestStatus(RepairRequestStatus.PROCESSED_STATUS);
        repairRecordDao.save(repairRecord);


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
