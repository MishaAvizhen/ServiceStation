package service.converters.impl;

import dao.RepairRecordDao;
import dao.RepairRequestDao;
import dao.impl.InMemoryRepairRecordDao;
import dao.impl.InMemoryRepairRequestDao;
import entity.RepairRecord;
import entity.RepairRequest;
import entity.constants.RepairRequestStatus;
import service.converters.RepairRecordConverterService;
import service.dto.RepairRecordRegistrationDto;

public class RepairRecordConverterImpl implements RepairRecordConverterService {
    private RepairRequestDao repairRequestDao = InMemoryRepairRequestDao.getInstance();
    private RepairRecordDao repairRecordDao = InMemoryRepairRecordDao.getInstance();

    private static RepairRecordConverterImpl repairRecordConverter;

    private RepairRecordConverterImpl() {
    }

    public static RepairRecordConverterImpl getInstance() {
        if (repairRecordConverter == null) {
            repairRecordConverter = new RepairRecordConverterImpl();
        }
        return repairRecordConverter;
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
}
