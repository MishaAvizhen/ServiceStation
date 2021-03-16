package entity;


import java.io.Serializable;

public class RepairRecord extends BaseEntity {

    private String repairRecordDescription;
    private Long detailPrice;
    private Long workPrice;
    private String otherNotes;
    private RepairRequest repairRequest;

    public RepairRecord() {
    }

    public RepairRecord( String repairRecordDescription, Long detailPrice, Long workPrice, String otherNotes, RepairRequest repairRequest) {
        this.repairRecordDescription = repairRecordDescription;
        this.detailPrice = detailPrice;
        this.workPrice = workPrice;
        this.otherNotes = otherNotes;
        this.repairRequest = repairRequest;
    }

    public String getRepairRecordDescription() {
        return repairRecordDescription;
    }

    public void setRepairRecordDescription(String repairRecordDescription) {
        this.repairRecordDescription = repairRecordDescription;
    }

    public Long getDetailPrice() {
        return detailPrice;
    }

    public void setDetailPrice(Long detailPrice) {
        this.detailPrice = detailPrice;
    }

    public Long getWorkPrice() {
        return workPrice;
    }

    public void setWorkPrice(Long workPrice) {
        this.workPrice = workPrice;
    }

    public String getOtherNotes() {
        return otherNotes;
    }

    public void setOtherNotes(String otherNotes) {
        this.otherNotes = otherNotes;
    }

    public RepairRequest getRepairRequest() {
        return repairRequest;
    }

    public void setRepairRequest(RepairRequest repairRequest) {
        this.repairRequest = repairRequest;
    }

    @Override
    public String toString() {
        return "RepairRecord{" +
                "repairRecordDescription='" + repairRecordDescription + '\'' +
                ", detailPrice=" + detailPrice +
                ", workPrice=" + workPrice +
                ", otherNotes='" + otherNotes + '\'' +
                ", repairRequest=" + repairRequest +
                "} " + super.toString();
    }
}
