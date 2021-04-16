package dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
