package entity;


import entity.consts.SlotStatus;

import java.util.Date;

public class Appointment extends BaseEntity {
    private User client;
    private User master;
    private SlotStatus slotStatus;
    private Date startDate;
    private Date endDate;
    private String notes;

    public Appointment() {
    }

    public Appointment(User client, User master, SlotStatus slotStatus, Date startDate, Date endDate, String notes) {
        this.client = client;
        this.master = master;
        this.slotStatus = slotStatus;
        this.startDate = startDate;
        this.endDate = endDate;
        this.notes = notes;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public User getMaster() {
        return master;
    }

    public void setMaster(User master) {
        this.master = master;
    }

    public SlotStatus getSlotStatus() {
        return slotStatus;
    }

    public void setSlotStatus(SlotStatus slotStatus) {
        this.slotStatus = slotStatus;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "client=" + client +
                ", master=" + master +
                ", slotStatus=" + slotStatus +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", notes='" + notes + '\'' +
                "} " + super.toString();
    }
}
