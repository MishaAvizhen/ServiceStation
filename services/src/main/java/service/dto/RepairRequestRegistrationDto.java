package service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import entity.enums.RepairRequestStatus;
import io.swagger.annotations.ApiModel;

import java.util.Date;

@ApiModel(description = "Repair request entity ")
public class RepairRequestRegistrationDto {
    private Date dateOfRequest;
    private RepairRequestStatus repairRequestStatus;
    private String carRemark;
    private String repairRequestDescription;
    private String username;
    private AppointmentSlotDto appointmentSlotDto;

    private RepairRequestRegistrationDto(Builder builder) {

        this.dateOfRequest = builder.dateOfRequest;
        this.repairRequestStatus = builder.repairRequestStatus;
        this.carRemark = builder.carRemark;
        this.repairRequestDescription = builder.repairRequestDescription;
        this.username = builder.username;
        this.appointmentSlotDto = builder.appointmentSlotDto;
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

    public AppointmentSlotDto getAppointmentSlotDto() {
        return appointmentSlotDto;
    }

    public static class Builder {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
        private Date dateOfRequest;
        private RepairRequestStatus repairRequestStatus;
        private String carRemark;
        private String repairRequestDescription;
        private String username;
        private AppointmentSlotDto appointmentSlotDto;

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

        public Builder setAppointmentSlotDto(AppointmentSlotDto appointmentSlotDto) {
            this.appointmentSlotDto = appointmentSlotDto;
            return this;
        }

        public RepairRequestRegistrationDto build() {
            return new RepairRequestRegistrationDto(this);
        }
    }

    @Override
    public String toString() {
        return "RepairRequestRegistrationDto{" +
                "dateOfRequest=" + dateOfRequest +
                ", repairRequestStatus=" + repairRequestStatus +
                ", carRemark='" + carRemark + '\'' +
                ", repairRequestDescription='" + repairRequestDescription + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
