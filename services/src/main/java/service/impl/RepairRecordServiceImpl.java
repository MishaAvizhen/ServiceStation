package service.impl;


import dao.RepairRecordDao;
import dao.RepairRequestDao;
import dao.UserDao;
import dao.impl.InMemoryRepairRecordDao;
import dao.impl.InMemoryRepairRequestDao;
import dao.impl.InMemoryUserDao;
import entity.RepairRecord;
import entity.RepairRequest;
import entity.User;
import entity.util.RepairRequestStatus;
import service.RepairRecordService;
import service.UserService;
import service.dto.RepairRecordRegistrationDto;

import java.util.ArrayList;
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
        repairRecordDao.save(repairRecord);


    }
}
