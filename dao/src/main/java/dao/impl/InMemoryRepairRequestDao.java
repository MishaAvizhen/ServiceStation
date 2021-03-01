package dao.impl;

import dao.InMemoryCommonDao;
import dao.RepairRecordDao;
import dao.RepairRequestDao;
import entity.RepairRequest;

public class InMemoryRepairRequestDao extends InMemoryCommonDao<RepairRequest> implements RepairRequestDao {
    private static InMemoryRepairRequestDao inMemoryRepairRequestDao;

    private InMemoryRepairRequestDao() {
    }

    public static InMemoryRepairRequestDao getInstance() {
        if (inMemoryRepairRequestDao == null) {
            inMemoryRepairRequestDao = new InMemoryRepairRequestDao();
        }
        return inMemoryRepairRequestDao;
    }
}
