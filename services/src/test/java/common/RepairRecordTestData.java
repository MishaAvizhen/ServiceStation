package common;

import entity.RepairRecord;

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
        saveTestRepairRecord(builtRepairRecordForTest(2L, 2L));
        saveTestRepairRecord(builtRepairRecordForTest(3L, 3L));

    }

    public List<RepairRecord> getAllRepairRecordForTest() {
        return new ArrayList<>(idToTestRepairRecordMap.values());
    }
    public void deleteRepairRecordById(Long repairRecordId) {
        RepairRecord repairRecordById = getRepairRecordById(repairRecordId);
        if (repairRecordById != null) {
            idToTestRepairRecordMap.remove(repairRecordById.getId());

        }

    }

    public RepairRecord getRepairRecordById(Long repairRecordId) {
        for (RepairRecord repairRecord : idToTestRepairRecordMap.values()) {
            if (repairRecord.getId().equals(repairRecordId)) {
                return repairRecord;
            }
        }
        return null;
    }

    public RepairRecord updateRepairRecord(RepairRecord testRepairRecord) {
        saveTestRepairRecord(testRepairRecord);
        return testRepairRecord;
    }

    public RepairRecord saveTestRepairRecord(RepairRecord repairRecord) {
        idToTestRepairRecordMap.put(repairRecord.getId(), repairRecord);
        return repairRecord;

    }

    private RepairRecord builtRepairRecordForTest(Long repairRecordId, Long repairRequestId) {
        RepairRecord repairRecord = new RepairRecord();
        repairRecord.setId(repairRecordId);
        repairRecord.setOtherNotes("testNotes");
        repairRecord.setDetailPrice(100L);
        repairRecord.setRepairRecordDescription("test repair record description");
        repairRecord.setWorkPrice(222L);
        repairRecord.setRepairRequest(null);
        return repairRecord;
    }
}
