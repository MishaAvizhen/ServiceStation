package service;


import entity.RepairRecord;
import service.dto.RepairRecordRegistrationDto;

import java.util.List;

public interface RepairRecordService {

    List<RepairRecord> findAllRepairRecords();

    void deleteRepairRecordByUsernameAndRepairRecordDescription(String username, String repairRecordDescription);

    void registerRepairRecord(RepairRecordRegistrationDto repairRecordRegistrationDto);

    void updateRepairRecord(RepairRecordRegistrationDto repairRecordRegistrationDto, RepairRecord repairRecordToUpdate);

    RepairRecord findRepairRecordByUsernameAndRepairRecordDescription(String username, String repairRecordDescription);
}
