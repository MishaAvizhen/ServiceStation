package dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Repair request entity for web registration")
public class RepairRequestRegistrationWebDto {

    private String clientUsername;
    private String repairRequestDescription;
    private String carRemark;
    private AppointmentSlotWebDto appointmentSlotWebDto;
}
