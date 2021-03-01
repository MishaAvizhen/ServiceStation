package service;


import entity.RepairRecord;
import entity.RepairRequest;
import service.dto.RepairRecordRegistrationDto;

import java.util.List;

public interface RepairRecordService {


    List<RepairRecord> findAllRepairRecords();
    void createRepairRecord(RepairRecordRegistrationDto repairRecordRegistrationDto);

}
