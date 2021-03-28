package dto;

import entity.consts.RepairRequestStatus;

import java.util.Date;

public class RepairRequestWebDto {
    private Long requestId;
    private Date dateOfRequest;
    private RepairRequestStatus repairRequestStatus;
    private String carRemark;
    private String repairRequestDescription;
    private String username;

    public RepairRequestWebDto() {
    }

    public RepairRequestWebDto(Long requestId, Date dateOfRequest, RepairRequestStatus repairRequestStatus,
                               String carRemark, String repairRequestDescription, String username) {
        this.requestId = requestId;
        this.dateOfRequest = dateOfRequest;
        this.repairRequestStatus = repairRequestStatus;
        this.carRemark = carRemark;
        this.repairRequestDescription = repairRequestDescription;
        this.username = username;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
