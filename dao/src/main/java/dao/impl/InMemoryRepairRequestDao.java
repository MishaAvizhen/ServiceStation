package dao.impl;

import dao.AbstractCRUDDao;
import dao.RepairRequestDao;
import entity.RepairRequest;

public class InMemoryRepairRequestDao extends AbstractCRUDDao<RepairRequest> implements RepairRequestDao {
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
