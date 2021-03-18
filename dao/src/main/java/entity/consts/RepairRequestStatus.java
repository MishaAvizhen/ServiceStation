package entity.consts;


public enum RepairRequestStatus {
    PROCESSED_STATUS("PROCESSED"),
    CANCELLED_STATUS("CANCELLED"),
    IN_PROGRESS_STATUS("IN_PROGRESS");

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
