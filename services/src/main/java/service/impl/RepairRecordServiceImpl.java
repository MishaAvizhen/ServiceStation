package service.impl;


import com.google.common.base.Preconditions;
import entity.RepairRecord;
import entity.RepairRequest;
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
import java.util.Optional;

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
    public RepairRecord findRepairRecordById(Long repairRecordId) {
        log.info(String.format("Find repair record  with id= {%s}", repairRecordId));
        Optional<RepairRecord> recordOptional = repairRecordRepository.findById(repairRecordId);
        return recordOptional.orElse(null);
    }

    @Override
    public void deleteRepairRecordByUsernameAndRepairRecordDescription(String username, String repairRecordDescription) {
        log.info(String.format("delete  repair record of user: {%s} with repair record description: {%s}", username, repairRecordDescription));
        RepairRecord repairRecord = repairRecordRepository.findAll().stream()
                .filter(record -> record.getRepairRequest().getUser().getUsername().equals(username) &&
                        record.getRepairRecordDescription().equals(repairRecordDescription))
                .findFirst().orElse(null);
        if (repairRecord != null) {
            repairRecordRepository.delete(repairRecord);

        }
    }

    @Override
    public RepairRecord registerRepairRecord(RepairRecordRegistrationDto repairRecordRegistrationDto) {
        RepairRequest repairRequestToCompare = repairRequestRepository.findById(repairRecordRegistrationDto.getRepairRequestId()).orElse(null);
        Preconditions.checkNotNull(repairRequestToCompare, "Error!!!  RepairRequest with id " +
                repairRecordRegistrationDto.getRepairRequestId() + " not found");
        log.info(String.format(" Error!!!  RepairRequest with id {%s} not found", repairRecordRegistrationDto.getRepairRequestId()));
        RepairRecord repairRecord = repairRecordConverter.convertToEntity(repairRecordRegistrationDto);
        RepairRequestStatus repairRequestStatus = repairRecord.getRepairRequest().getRepairRequestStatus();
        if (repairRequestStatus.equals(RepairRequestStatus.PROCESSED)) {
            log.info(String.format("  repair record with info: {%s} was created ", repairRecordRegistrationDto.toString()));
            throw new IllegalArgumentException("Error!!! Status is incorrect");
        }

        RepairRecord record = repairRecordRepository.save(repairRecord);
        repairRecord.getRepairRequest().setRepairRequestStatus(RepairRequestStatus.PROCESSED);
        log.info(String.format("  repair record with info: {%s} was created ", repairRecordRegistrationDto.toString()));
        repairRequestRepository.save(repairRecord.getRepairRequest());
        return record;

    }

    @Override
    public RepairRecord updateRepairRecord(RepairRecordRegistrationDto repairRecordRegistrationDto, RepairRecord repairRecordToUpdate) {
        Long id = repairRecordToUpdate.getRepairRequest().getId();
        Long repairRequestToCompare = repairRecordRegistrationDto.getRepairRequestId();

        if (!id.equals(repairRequestToCompare)) {
            log.info(String.format("Requests id are not equals, {%s} != {%s}", id, repairRequestToCompare));
            throw new IllegalArgumentException("Requests id are not equals");
        }
        log.info(String.format("  repair record with info: {%s} was updated ", repairRecordRegistrationDto.toString()));
        RepairRecord repairRecord = repairRecordConverter.convertToExistingEntity(repairRecordRegistrationDto, repairRecordToUpdate);
        return repairRecordRepository.saveAndFlush(repairRecord);
    }

    @Override
    public RepairRecord findRepairRecordByUsernameAndRepairRecordDescription(String username, String repairRecordDescription) {
        log.info(String.format("  repair record for user: {%s} \n and description: {%s} find ", username, repairRecordDescription));

        return repairRecordRepository.findAll().stream()
                .filter(record -> record.getRepairRequest().getUser().getUsername().equals(username)
                        && record.getRepairRecordDescription().equals(repairRecordDescription))
                .findAny().orElse(null);

    }

    @Override
    public void deleteRepairRecordById(Long repairRecordId) {
        log.info(String.format("Delete repair record with id=  {%s}", repairRecordId));
        RepairRecord repairRecordAfterUpdate = repairRecordRepository.getOne(repairRecordId);
        repairRecordAfterUpdate.getRepairRequest().setRepairRequestStatus(RepairRequestStatus.IN_PROGRESS);
        repairRecordRepository.deleteById(repairRecordId);
    }
}
