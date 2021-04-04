package common;

import entity.RepairRecord;
import entity.RepairRequest;
import entity.consts.RepairRequestStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepairRecordTestData {
    private Map<Long, RepairRecord> idToTestRepairRecordMap = new HashMap<>();
    private static RepairRecordTestData INSTANCE = null;

    private RepairRecordTestData() {
        initRepairRecordTestData();

    }

    public static RepairRecordTestData getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RepairRecordTestData();
        }
        return INSTANCE;
    }

    private void initRepairRecordTestData() {
        saveTestRepairRecord(builtRepairRecordForTest(1L, 1L));

    }

    public List<RepairRecord> getAllRepairRecordForTest() {
        return new ArrayList<>(idToTestRepairRecordMap.values());
    }
    public RepairRecord deleteRepairRecordById(Long repairRecordId) {
        RepairRecord repairRecordToDelete = getRepairRecordById(repairRecordId);
        if (repairRecordToDelete != null) {
            idToTestRepairRecordMap.remove(repairRecordToDelete.getId());

        }

        return repairRecordToDelete;
    }

    public RepairRecord getRepairRecordById(Long repairRecordId) {
        for (RepairRecord repairRecord : idToTestRepairRecordMap.values()) {
            if (repairRecord.getId().equals(repairRecordId)) {
                return repairRecord;
            }
        }
        return null;
    }
    public long getNextId() {
        return idToTestRepairRecordMap.size() + 1L;
    }

    public RepairRecord updateRepairRecord(RepairRecord testRepairRecord) {
        saveTestRepairRecord(testRepairRecord);
        return testRepairRecord;
    }

    public RepairRecord saveTestRepairRecord(RepairRecord testRepairRecord) {
        if (testRepairRecord.getId() == null) {
            testRepairRecord.setId(getNextId());
        }
        idToTestRepairRecordMap.put(testRepairRecord.getId(), testRepairRecord);
        return testRepairRecord;
    }

    private RepairRecord builtRepairRecordForTest(Long repairRecordId, Long repairRequestId) {
        RepairRecord repairRecord = new RepairRecord();
        repairRecord.setId(repairRecordId);
        repairRecord.setOtherNotes("testNotes");
        repairRecord.setDetailPrice(100L);
        repairRecord.setRepairRecordDescription("test repair record description");
        repairRecord.setWorkPrice(222L);
        RepairRequest repairRequestById = RepairRequestTestData.getInstance().getRepairRequestById(repairRequestId);
        repairRecord.setRepairRequest(repairRequestById);
        repairRequestById.setRepairRequestStatus(RepairRequestStatus.PROCESSED);
        repairRequestById.setRepairRecord(repairRecord);
        return repairRecord;
    }
}
