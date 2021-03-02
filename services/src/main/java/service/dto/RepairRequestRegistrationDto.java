package service.dto;

import entity.User;
import entity.util.RepairRequestStatus;

import java.util.Date;

public class RepairRequestRegistrationDto {
    private Date dateOfRequest;
    private RepairRequestStatus repairRequestStatus;
    private String carRemark;
    private String repairRequestDescription;
    private String username;

    private RepairRequestRegistrationDto(Builder builder) {

        this.dateOfRequest = builder.dateOfRequest;
        this.repairRequestStatus = builder.repairRequestStatus;
        this.carRemark = builder.carRemark;
        this.repairRequestDescription = builder.repairRequestDescription;
        this.username = builder.username;
    }



    public Date getDateOfRequest() {
        return dateOfRequest;
    }

    public RepairRequestStatus getRepairRequestStatus() {
        return repairRequestStatus;
    }

    public String getCarRemark() {
        return carRemark;
    }

    public String getRepairRequestDescription() {
        return repairRequestDescription;
    }

    public String getUsername() {
        return username;
    }

    public static class Builder {
        private Date dateOfRequest;
        private RepairRequestStatus repairRequestStatus;
        private String carRemark;
        private String repairRequestDescription;
        private String username;

        public Builder() {
        }

        public Builder setDateOfRequest(Date dateOfRequest) {
            this.dateOfRequest = dateOfRequest;
            return this;
        }

        public Builder setRepairRequestStatus(RepairRequestStatus repairRequestStatus) {
            this.repairRequestStatus = repairRequestStatus;
            return this;
        }

        public Builder setCarRemark(String carRemark) {
            this.carRemark = carRemark;
            return this;
        }

        public Builder setRepairRequestDescription(String repairRequestDescription) {
            this.repairRequestDescription = repairRequestDescription;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public RepairRequestRegistrationDto build() {
            return new RepairRequestRegistrationDto(this);
        }
    }
}