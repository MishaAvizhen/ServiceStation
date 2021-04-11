package dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RepairRequestRegistrationWebDto {
    private String clientUsername;
    private String repairRequestDescription;
    private String carRemark;
    private AppointmentSlotWebDto appointmentSlotWebDto;
}
