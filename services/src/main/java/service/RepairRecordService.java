package service;


import entity.RepairRecord;
import service.dto.RepairRecordRegistrationDto;

import java.util.List;

public interface RepairRecordService {

    List<RepairRecord> findAllRepairRecords();

    RepairRecord findRepairRecordById(Long repairRecordId);

    void deleteRepairRecordByUsernameAndRepairRecordDescription(String username, String repairRecordDescription);

    RepairRecord registerRepairRecord(RepairRecordRegistrationDto repairRecordRegistrationDto);

    RepairRecord updateRepairRecord(RepairRecordRegistrationDto repairRecordRegistrationDto, RepairRecord repairRecordToUpdate);

    RepairRecord findRepairRecordByUsernameAndRepairRecordDescription(String username, String repairRecordDescription);


    List<RepairRecord> findRepairRecordsByUsername(String username);

    void deleteRepairRecordById(Long repairRecordId);

}
