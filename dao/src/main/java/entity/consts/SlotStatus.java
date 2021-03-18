package entity.consts;


public enum SlotStatus {
    FREE_STATUS("FREE"),
    BUSY_STATUS("BUSY"),
    NOT_AVAILABLE_STATUS("NOT_AVAILABLE");

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
