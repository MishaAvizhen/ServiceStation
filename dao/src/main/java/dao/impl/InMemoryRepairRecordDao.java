package dao.impl;


import dao.InMemoryCommonDao;
import dao.RepairRecordDao;
import entity.RepairRecord;

public class InMemoryRepairRecordDao extends InMemoryCommonDao<RepairRecord> implements RepairRecordDao {

    private static InMemoryRepairRecordDao inMemoryRepairRecordDao;

    private InMemoryRepairRecordDao() {
    }


    public static InMemoryRepairRecordDao getInstance() {
        if (inMemoryRepairRecordDao == null) {
            inMemoryRepairRecordDao = new InMemoryRepairRecordDao();
        }
        return inMemoryRepairRecordDao;
    }
}
