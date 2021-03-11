package dao.impl;


import dao.AbstractCRUDDao;
import dao.RepairRecordDao;
import entity.RepairRecord;

public class InMemoryRepairRecordDao extends AbstractCRUDDao<RepairRecord> implements RepairRecordDao {

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
