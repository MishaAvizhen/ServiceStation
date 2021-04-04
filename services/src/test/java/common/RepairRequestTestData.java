package common;

import entity.RepairRequest;
import entity.consts.RepairRequestStatus;

import java.util.*;

public class RepairRequestTestData {
    private Map<Long, RepairRequest> idToTestRepairRequestMap = new HashMap<>();
    private static RepairRequestTestData INSTANCE = null;

    private RepairRequestTestData() {
        initRepairRequestTestData();

    }

    public static RepairRequestTestData getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RepairRequestTestData();
        }
        return INSTANCE;
    }

    public List<RepairRequest> getAllTestRepairRequest() {
        return new ArrayList<>(idToTestRepairRequestMap.values());

    }

    public RepairRequest getRepairRequestById(Long repairRequestId) {
        for (RepairRequest repairRequest : idToTestRepairRequestMap.values()) {
            if (repairRequest.getId().equals(repairRequestId)) {
                return repairRequest;
            }
        }
        return null;
    }

    private void initRepairRequestTestData() {
        saveTestRepairRequest(buildRepairRequestForTest(1L, "user", 1L));
        saveTestRepairRequest(buildRepairRequestForTest(2L, "userToUpdate", 2L));
        saveTestRepairRequest(buildRepairRequestForTest(3L, "userToDelete", 3L));

    }

    public RepairRequest deleteRepairRequestById(Long repairRequestId) {
        RepairRequest repairRequestToDelete = getRepairRequestById(repairRequestId);
        if (repairRequestToDelete != null) {
            idToTestRepairRequestMap.remove(repairRequestToDelete.getId());

        }
        return repairRequestToDelete;
    }

    public RepairRequest updateTestRepairRequest(RepairRequest testRepairRequest) {
        saveTestRepairRequest(testRepairRequest);
        return testRepairRequest;
    }

    public RepairRequest saveTestRepairRequest(RepairRequest testRepairRequest) {
        if (testRepairRequest.getId() == null) {
            testRepairRequest.setId(getNextId());
        }
        idToTestRepairRequestMap.put(testRepairRequest.getId(), testRepairRequest);
        return testRepairRequest;
    }

    public long getNextId() {
        return idToTestRepairRequestMap.size() + 1L;
    }

    private RepairRequest buildRepairRequestForTest(Long repairRequestId, String username, Long repairRecordId) {
        RepairRequest repairRequest = new RepairRequest();
        repairRequest.setId(repairRequestId);
        repairRequest.setCarRemark("testCar");
        repairRequest.setDateOfRequest(new Date());
        repairRequest.setRepairRequestDescription("repair request description");
        repairRequest.setRepairRequestStatus(RepairRequestStatus.IN_PROGRESS);
        repairRequest.setUser(UserTestData.getInstance().getTestUserByUsername(username));
        repairRequest.setRepairRecord(null);
        return repairRequest;
    }
}
