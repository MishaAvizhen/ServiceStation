package service;


import entity.RepairRecord;

import java.util.List;

public interface RepairRecordService {


    List<RepairRecord> findAllRepairRecords();

    void deleteRepairRecordByUsernameAndRepairRecordDescription(String username, String repairRecordDescription);


}
