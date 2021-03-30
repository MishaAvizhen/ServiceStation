package entity;


import entity.consts.SlotStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = " appointment")
public class Appointment extends BaseEntity {
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "client_id")
    private User client;
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "master_id")
    private User master;
    @Column(name = "slot_status")
    @Enumerated(EnumType.STRING)
    private SlotStatus slotStatus;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    @Column(name = "notes")
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

    public Appointment setClient(User client) {
        this.client = client;
        return this;
    }

    public User getMaster() {
        return master;
    }

    public Appointment setMaster(User master) {
        this.master = master;
        return this;
    }

    public SlotStatus getSlotStatus() {
        return slotStatus;
    }

    public Appointment setSlotStatus(SlotStatus slotStatus) {
        this.slotStatus = slotStatus;
        return this;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Appointment setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Appointment setEndDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public Appointment setNotes(String notes) {
        this.notes = notes;
        return this;
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
