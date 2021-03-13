package entity;

import entity.constants.RepairRequestStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "repair_request")
public class RepairRequest extends BaseEntity {
    @Column(name = "date_of_repair")
    private Date dateOfRequest;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RepairRequestStatus repairRequestStatus;
    @Column(name = "car_remark")
    private String carRemark;
    @Column(name = "repair_request_description")
    private String repairRequestDescription;
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne(cascade = {CascadeType.REFRESH}, mappedBy = "repairRequest", fetch = FetchType.EAGER)
    private RepairRecord repairRecord;


    public RepairRequest() {
    }

    public RepairRequest(Date dateOfRequest, RepairRequestStatus repairRequestStatus, String carRemark,
                         String repairRequestDescription, User user, RepairRecord repairRecord) {
        this.dateOfRequest = dateOfRequest;
        this.repairRequestStatus = repairRequestStatus;
        this.carRemark = carRemark;
        this.repairRequestDescription = repairRequestDescription;
        this.user = user;
        this.repairRecord = repairRecord;
    }

    public Date getDateOfRequest() {
        return dateOfRequest;
    }

    public RepairRequest setDateOfRequest(Date dateOfRequest) {
        this.dateOfRequest = dateOfRequest;
        return this;
    }

    public RepairRequestStatus getRepairRequestStatus() {
        return repairRequestStatus;
    }

    public RepairRequest setRepairRequestStatus(RepairRequestStatus repairRequestStatus) {
        this.repairRequestStatus = repairRequestStatus;
        return this;
    }

    public String getCarRemark() {
        return carRemark;
    }

    public RepairRequest setCarRemark(String carRemark) {
        this.carRemark = carRemark;
        return this;
    }

    public String getRepairRequestDescription() {
        return repairRequestDescription;
    }

    public RepairRequest setRepairRequestDescription(String repairRequestDescription) {
        this.repairRequestDescription = repairRequestDescription;
        return this;
    }

    public User getUser() {
        return user;
    }

    public RepairRequest setUser(User user) {
        this.user = user;
        return this;
    }

    public RepairRecord getRepairRecord() {
        return repairRecord;
    }

    public RepairRequest setRepairRecord(RepairRecord repairRecord) {
        this.repairRecord = repairRecord;
        return this;
    }

    @Override
    public String toString() {
        return "RepairRequest{" +
                "dateOfRequest=" + dateOfRequest +
                ", repairRequestStatus=" + repairRequestStatus +
                ", carRemark='" + carRemark + '\'' +
                ", repairRequestDescription='" + repairRequestDescription + '\'' +
                "} " + super.toString();
    }
}
