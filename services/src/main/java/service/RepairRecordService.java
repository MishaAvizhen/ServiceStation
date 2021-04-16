package service;


import entity.RepairRecord;
import service.dto.RepairRecordFilterDto;
import service.dto.RepairRecordRegistrationDto;

import java.util.List;

public interface RepairRecordService {

    List<RepairRecord> findAllRepairRecords();

    RepairRecord findRepairRecordById(Long repairRecordId);

    void deleteRepairRecordByUsernameAndRepairRecordDescription(String username, String repairRecordDescription);

    RepairRecord registerRepairRecord(RepairRecordRegistrationDto repairRecordRegistrationDto);

    RepairRecord updateRepairRecord(RepairRecordRegistrationDto repairRecordRegistrationDto, Long id);

    List<RepairRecord> findRepairRecordsByUsername(String username);

    void deleteById(Long id);

    List<RepairRecord> filterRepairRecord(RepairRecordFilterDto filterDto);

}
