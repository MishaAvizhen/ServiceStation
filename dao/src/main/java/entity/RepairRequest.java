package entity;

import entity.constants.RepairRequestStatus;

import java.util.Date;

public class RepairRequest extends BaseEntity {

    private Date dateOfRequest;
    private RepairRequestStatus repairRequestStatus;
    private String carRemark;
    private String repairRequestDescription;
    private User user;

    public RepairRequest() {
    }

    public RepairRequest(User user, String repairRequestDescription, String carRemark, RepairRequestStatus repairRequestStatus, Date dateOfRequest) {
        this.user = user;
        this.repairRequestDescription = repairRequestDescription;
        this.carRemark = carRemark;
        this.repairRequestStatus = repairRequestStatus;
        this.dateOfRequest = dateOfRequest;

    }

    public Date getDateOfRequest() {
        return dateOfRequest;
    }

    public void setDateOfRequest(Date dateOfRequest) {
        this.dateOfRequest = dateOfRequest;
    }

    public RepairRequestStatus getRepairRequestStatus() {
        return repairRequestStatus;
    }

    public void setRepairRequestStatus(RepairRequestStatus repairRequestStatus) {
        this.repairRequestStatus = repairRequestStatus;
    }

    public String getCarRemark() {
        return carRemark;
    }

    public void setCarRemark(String carRemark) {
        this.carRemark = carRemark;
    }

    public String getRepairRequestDescription() {
        return repairRequestDescription;
    }

    public void setRepairRequestDescription(String repairRequestDescription) {
        this.repairRequestDescription = repairRequestDescription;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "RepairRequest{" +
                "dateOfRequest=" + dateOfRequest +
                ", repairRequestStatus=" + repairRequestStatus +
                ", carRemark='" + carRemark + '\'' +
                ", repairRequestDescription='" + repairRequestDescription + '\'' +
                ", user=" + user +
                "} " + super.toString();
    }
}
