package entity.consts;


public enum RepairRequestStatus {
    PROCESSED,
    CANCELLED,
    IN_PROGRESS;


    public static RepairRequestStatus defineRepairRequestStatusByName(String inputStatusName) {
        for (RepairRequestStatus repairRequestStatus : values()) {
            if (inputStatusName.equals(repairRequestStatus.name())) {
                return repairRequestStatus;
            }
        }
        return null;
    }
}
