package service.impl;


import entity.RepairRecord;
import entity.consts.RepairRequestStatus;
import org.apache.log4j.Logger;
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
    private static final Logger log = Logger.getLogger(RepairRecordServiceImpl.class);

    private RepairRecordRepository repairRecordRepository;

    private RepairRequestRepository repairRequestRepository;

    private RepairRecordConverter repairRecordConverter;

    @Autowired
    public RepairRecordServiceImpl(RepairRecordRepository repairRecordRepository,
                                   RepairRequestRepository repairRequestRepository,
                                   RepairRecordConverter repairRecordConverter) {
        this.repairRecordRepository = repairRecordRepository;
        this.repairRequestRepository = repairRequestRepository;
        this.repairRecordConverter = repairRecordConverter;
    }

    @Override
    public List<RepairRecord> findAllRepairRecords() {
        log.info(String.format("Find all repair records"));
        return repairRecordRepository.findAll();
    }


    @Override
    public void deleteRepairRecordByUsernameAndRepairRecordDescription(String username, String repairRecordDescription) {
        log.info(String.format("delete  repair record of user: {%s} with repair record description: {%s}", username, repairRecordDescription));
        log.debug(String.format("delete  repair record of user: {%s} with repair record description: {%s}", username, repairRecordDescription));
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
        log.info(String.format("  repair record with info: {%s} was created ", repairRecordRegistrationDto.toString()));
        log.debug(String.format("  repair record with info: {%s} was created ", repairRecordRegistrationDto.toString()));
        RepairRecord repairRecord = repairRecordConverter.convertToEntity(repairRecordRegistrationDto);
        repairRecordRepository.save(repairRecord);
        repairRecord.getRepairRequest().setRepairRequestStatus(RepairRequestStatus.PROCESSED);
        repairRequestRepository.save(repairRecord.getRepairRequest());
    }

    @Override
    public void updateRepairRecord(RepairRecordRegistrationDto repairRecordRegistrationDto, RepairRecord repairRecordToUpdate) {
        log.info(String.format("  repair record with info: {%s} was updated ", repairRecordRegistrationDto.toString()));
        log.debug(String.format("  repair record with info: {%s} was updated ", repairRecordRegistrationDto.toString()));
        RepairRecord repairRecord = repairRecordConverter.convertToExistingEntity(repairRecordRegistrationDto, repairRecordToUpdate);
        repairRecordRepository.saveAndFlush(repairRecord);
    }

    @Override
    public RepairRecord findRepairRecordByUsernameAndRepairRecordDescription(String username, String repairRecordDescription) {
        log.info(String.format("  repair record for user: {%s} \n and description: {%s} find ", username, repairRecordDescription));
        log.debug(String.format("  repair record for user: {%s} \n and description: {%s} find ", username, repairRecordDescription));

        return repairRecordRepository.findAll().stream()
                .filter(record -> record.getRepairRequest().getUser().getUsername().equals(username)
                        && record.getRepairRecordDescription().equals(repairRecordDescription))
                .findAny().orElse(null);

    }
}
