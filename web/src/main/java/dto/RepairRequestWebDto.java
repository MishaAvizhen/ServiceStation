package dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import entity.consts.RepairRequestStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import lombok.*;
import service.dto.AppointmentSlotDto;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Repair request entity")
public class RepairRequestWebDto {

    private Long requestId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Date dateOfRequest;
    private RepairRequestStatus repairRequestStatus;
    private String carRemark;
    private String repairRequestDescription;
    private String username;
    private AppointmentSlotWebDto appointmentSlotWebDto;

}
