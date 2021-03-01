package service;


import entity.RepairRecord;

import java.util.List;

public interface RepairRecordService {

    List<RepairRecord> getListRepairRecordsOfUser(Long userId);
    List<RepairRecord> FindAllRepairRecords();
    Long getFullDetailPrice();
    Long getFullWorkPrice();
}
