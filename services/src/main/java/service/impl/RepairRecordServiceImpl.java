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
import service.dto.RepairRecordFilterDto;
import service.dto.RepairRecordRegistrationDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        log.info("Find all repair records");
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
            log.info(String.format("  repair record with info: {%s} was not created ", repairRecordRegistrationDto.toString()));
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
    public List<RepairRecord> findRepairRecordsByUsername(String username) {
        log.info(String.format("Find  repair records of user: {%s} ", username));
        return repairRecordRepository.findByUsername(username);
        // TODO перенести на уровень sql
    }

    @Override
    public void deleteById(Long id) {
        log.info(String.format("Delete repair record with id=  {%s}", id));
        RepairRecord repairRecordAfterUpdate = repairRecordRepository.getOne(id);
        repairRecordAfterUpdate.getRepairRequest().setRepairRequestStatus(RepairRequestStatus.IN_PROGRESS);
        repairRecordRepository.deleteById(id);
    }

    @Override
    public List<RepairRecord> filterRepairRecord(RepairRecordFilterDto filterDto) {
        List<RepairRecord> allRepairRecords = repairRecordRepository.findAll();
        return allRepairRecords.stream()
                .filter(repairRecord -> filterDto.getUsername() == null ||
                        filterDto.getUsername().equals(repairRecord.getRepairRequest().getUser().getUsername()))
                .filter(repairRecord -> filterDto.getCarRemark() == null ||
                        filterDto.getCarRemark().equals(repairRecord.getRepairRequest().getCarRemark()))
                .filter(repairRecord -> filterDto.getId() == null || filterDto.getId().equals(repairRecord.getId().toString()))
                .collect(Collectors.toList());
    }
}
