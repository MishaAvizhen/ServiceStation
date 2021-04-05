package entity.consts;


public enum SlotStatus {
    FREE,
    BUSY,
    NOT_AVAILABLE;


    public static SlotStatus defineSlotStatusByName(String inputStatusName) {
        for (SlotStatus slotStatus : values()) {
            if (inputStatusName.equals(slotStatus.name())) {
                return slotStatus;
            }
        }
        return null;
    }
}
