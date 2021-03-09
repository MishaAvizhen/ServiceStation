package entity.constants;


public enum SlotStatus {
    FREE_STATUS("FREE_STATUS"),
    BUSY_STATUS("BUSY_STATUS"),
    NOT_AVAILABLE_STATUS("NOT_AVAILABLE_STATUS");

    private String slotStatusName;

    SlotStatus(String slotStatusName) {
        this.slotStatusName = slotStatusName;
    }

    public String getSlotStatusName() {
        return slotStatusName;
    }

    public static SlotStatus defineSlotStatusByName(String inputStatusName) {
        for (SlotStatus slotStatus : values()) {
            if (inputStatusName.equals(slotStatus.getSlotStatusName())) {
                return slotStatus;
            }
        }
        return null;
    }
}
