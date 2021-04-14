package dto;

import entity.User;
import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class RepairRequestRegistrationWebDto {
    private String clientUsername;
    private String repairRequestDescription;
    private String carRemark;
    private AppointmentSlotWebDto appointmentSlotWebDto;
}
