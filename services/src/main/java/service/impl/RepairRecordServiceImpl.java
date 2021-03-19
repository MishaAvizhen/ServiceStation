package service.impl;


import entity.RepairRecord;
import entity.consts.RepairRequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.RepairRecordRepository;
import repository.RepairRequestRepository;
import service.RepairRecordService;
import service.converters.impl.RepairRecordConverter;
import service.dto.RepairRecordRegistrationDto;

import java.util.List;

@Service

public class RepairRecordServiceImpl implements RepairRecordService {
    @Autowired
    private RepairRecordRepository repairRecordRepository;
    @Autowired
    private RepairRequestRepository repairRequestRepository;
    @Autowired
    private RepairRecordConverter repairRecordConverter;


    @Override
    public List<RepairRecord> findAllRepairRecords() {
        return repairRecordRepository.findAll();
    }


    @Override
    public void deleteRepairRecordByUsernameAndRepairRecordDescription(String username, String repairRecordDescription) {
        RepairRecord repairRecord = repairRecordRepository.findAll().stream()
                .filter(record -> record.getRepairRequest().getUser().getUsername().equals(username) &&
                        record.getRepairRecordDescription().equals(repairRecordDescription))
                .findFirst().orElse(null);
        if (repairRecord != null) {
            repairRecordRepository.delete(repairRecord);

        }
    }

    @Override
    public void registerRepairRecord(RepairRecordRegistrationDto repairRecordRegistrationDto) {
        RepairRecord repairRecord = repairRecordConverter.convertToEntity(repairRecordRegistrationDto);
        repairRecordRepository.save(repairRecord);
        repairRecord.getRepairRequest().setRepairRequestStatus(RepairRequestStatus.PROCESSED);
        repairRequestRepository.save(repairRecord.getRepairRequest());
    }

    @Override
    public void updateRepairRecord(RepairRecordRegistrationDto repairRecordRegistrationDto, RepairRecord repairRecordToUpdate) {
        RepairRecord repairRecord = repairRecordConverter.convertToExistingEntity(repairRecordRegistrationDto, repairRecordToUpdate);
        repairRecordRepository.saveAndFlush(repairRecord);
    }

    @Override
    public RepairRecord findRepairRecordByUsernameAndRepairRecordDescription(String username, String repairRecordDescription) {
        return repairRecordRepository.findAll().stream()
                .filter(record -> record.getRepairRequest().getUser().getUsername().equals(username)
                        && record.getRepairRecordDescription().equals(repairRecordDescription))
                .findAny().orElse(null);

    }
}
