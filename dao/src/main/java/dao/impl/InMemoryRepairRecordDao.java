package dao.impl;


import dao.AbstractCrudDao;
import dao.RepairRecordDao;
import entity.RepairRecord;

public class InMemoryRepairRecordDao extends AbstractCrudDao<RepairRecord> implements RepairRecordDao {

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
