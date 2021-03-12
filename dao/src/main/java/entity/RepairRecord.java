package entity;


import javax.persistence.*;

@Entity
@Table(name = " repair_record")
public class RepairRecord extends BaseEntity {
    @Column(name = "repair_record_description")
    private String repairRecordDescription;
    @Column(name = "detail_price")
    private Long detailPrice;
    @Column(name = "work_price")
    private Long workPrice;
    @Column(name = "other_notes")
    private String otherNotes;
    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "repair_request_id")
    private RepairRequest repairRequest;

    public RepairRecord() {
    }

    public RepairRecord(String repairRecordDescription, Long detailPrice, Long workPrice, String otherNotes, RepairRequest repairRequest) {
        this.repairRecordDescription = repairRecordDescription;
        this.detailPrice = detailPrice;
        this.workPrice = workPrice;
        this.otherNotes = otherNotes;
        this.repairRequest = repairRequest;
    }

    public String getRepairRecordDescription() {
        return repairRecordDescription;
    }

    public RepairRecord setRepairRecordDescription(String repairRecordDescription) {
        this.repairRecordDescription = repairRecordDescription;
        return this;
    }

    public Long getDetailPrice() {
        return detailPrice;
    }

    public RepairRecord setDetailPrice(Long detailPrice) {
        this.detailPrice = detailPrice;
        return this;
    }

    public Long getWorkPrice() {
        return workPrice;
    }

    public RepairRecord setWorkPrice(Long workPrice) {
        this.workPrice = workPrice;
        return this;
    }

    public String getOtherNotes() {
        return otherNotes;
    }

    public RepairRecord setOtherNotes(String otherNotes) {
        this.otherNotes = otherNotes;
        return this;
    }

    public RepairRequest getRepairRequest() {
        return repairRequest;
    }

    public RepairRecord setRepairRequest(RepairRequest repairRequest) {
        this.repairRequest = repairRequest;
        return this;
    }

    @Override
    public String toString() {
        return "RepairRecord{" +
                "repairRecordDescription='" + repairRecordDescription + '\'' +
                ", detailPrice=" + detailPrice +
                ", workPrice=" + workPrice +
                ", otherNotes='" + otherNotes + '\'' +
                "} " + super.toString();
    }
}
