package service;


import entity.RepairRecord;
import service.dto.RepairRecordRegistrationDto;

import java.util.List;

public interface RepairRecordService {


    List<RepairRecord> findAllRepairRecords();

    void createRepairRecord(RepairRecordRegistrationDto repairRecordRegistrationDto);

    void deleteRepairRecordByUsernameAndRepairRecordDescription(String username, String repairRecordDescription);


}
