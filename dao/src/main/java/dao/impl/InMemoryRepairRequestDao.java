package dao.impl;

import dao.AbstractCrudDao;
import dao.RepairRequestDao;
import entity.RepairRequest;

public class InMemoryRepairRequestDao extends AbstractCrudDao<RepairRequest> implements RepairRequestDao {
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
