package entity.constants;


public enum RepairRequestStatus {
    PROCESSED_STATUS("PROCESSED_STATUS"),
    CANCELLED_STATUS("CANCELLED_STATUS"),
    IN_PROGRESS_STATUS("IN_PROGRESS_STATUS");

    private String statusName;

    RepairRequestStatus(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }

    public static RepairRequestStatus defineRepairRequestStatusByName(String inputStatusName) {
        for (RepairRequestStatus repairRequestStatus : values()) {
            if (inputStatusName.equals(repairRequestStatus.statusName)) {
                return repairRequestStatus;
            }
        }
        return null;
    }
}
