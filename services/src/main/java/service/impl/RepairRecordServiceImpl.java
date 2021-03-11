package service.impl;


import dao.BeanManager;
import dao.RepairRecordDao;
import dao.RepairRequestDao;
import entity.RepairRecord;
import entity.constants.RepairRequestStatus;
import service.RepairRecordService;
import service.converters.impl.RepairRecordConverter;
import service.dto.RepairRecordRegistrationDto;

import java.util.List;

public class RepairRecordServiceImpl implements RepairRecordService {
    private RepairRecordDao repairRecordDao = BeanManager.getInstance().getRepairRecord();
    private RepairRequestDao repairRequestDao = BeanManager.getInstance().getRepairRequest();

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

    @Override
    public void registerRepairRecord(RepairRecordRegistrationDto repairRecordRegistrationDto) {
        RepairRecordConverter repairRecordConverter = new RepairRecordConverter();
        RepairRecord repairRecord = repairRecordConverter.convertToEntity(repairRecordRegistrationDto);
        repairRecordDao.save(repairRecord);
        repairRecord.getRepairRequest().setRepairRequestStatus(RepairRequestStatus.PROCESSED_STATUS);
        repairRequestDao.save(repairRecord.getRepairRequest());
    }

    @Override
    public void updateRepairRecord(RepairRecordRegistrationDto repairRecordRegistrationDto, RepairRecord repairRecordToUpdate) {
        RepairRecordConverter repairRecordConverter = new RepairRecordConverter();
        RepairRecord repairRecord = repairRecordConverter.convertToExistingEntity(repairRecordRegistrationDto, repairRecordToUpdate);
        repairRecordDao.update(repairRecord);
    }

    @Override
    public RepairRecord findRepairRecordByUsernameAndRepairRecordDescription(String username, String repairRecordDescription) {
        List<RepairRecord> allRepairRecords = repairRecordService.findAllRepairRecords();
        for (RepairRecord allRepairRecord : allRepairRecords) {
            if (allRepairRecord.getRepairRequest().getUser().getUsername().equals(username)
                    && allRepairRecord.getRepairRecordDescription().equals(repairRecordDescription)) {
                return allRepairRecord;
            }
        }
        return null;
    }
}
