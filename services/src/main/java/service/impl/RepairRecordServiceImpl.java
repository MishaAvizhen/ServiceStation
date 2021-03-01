package service.impl;


import dao.RepairRecordDao;
import dao.impl.InMemoryRepairRecordDao;
import entity.RepairRecord;
import service.RepairRecordService;

import java.util.ArrayList;
import java.util.List;

public class RepairRecordServiceImpl implements RepairRecordService {
    private RepairRecordDao repairRecordDao = InMemoryRepairRecordDao.getInstance();

    private static RepairRecordServiceImpl repairRecordService;
    private static Long fullWorkPrice = 0L;
    private static Long fullDetailPrice = 0L;

    private RepairRecordServiceImpl() {
    }

    public static RepairRecordServiceImpl getInstance() {
        if (repairRecordService == null) {
            repairRecordService = new RepairRecordServiceImpl();
        }
        return repairRecordService;
    }

    @Override
    public List<RepairRecord> getListRepairRecordsOfUser(Long userId) {
        List<RepairRecord> resultList = new ArrayList<>();
        List<RepairRecord> repairRecordList = repairRecordDao.findAll();
        for (RepairRecord record : repairRecordList) {
            if (record.getRepairRequest().getUser().getId().equals(userId)) {
                resultList.add(record);
            }
        }
        return resultList;
    }

    @Override
    public List<RepairRecord> FindAllRepairRecords() {
        return repairRecordDao.findAll();

    }

    @Override
    public Long getFullDetailPrice() {
        List<RepairRecord> repairRecordList = repairRecordDao.findAll();
        for (RepairRecord record : repairRecordList) {
            fullDetailPrice += record.getDetailPrice();
        }
        return fullDetailPrice;
    }

    @Override
    public Long getFullWorkPrice() {
        List<RepairRecord> repairRecordList = repairRecordDao.findAll();
        for (RepairRecord record : repairRecordList) {
            fullWorkPrice += record.getWorkPrice();
        }
        return fullWorkPrice;
    }
}
