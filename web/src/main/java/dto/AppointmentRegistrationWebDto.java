package dto;

import entity.User;
import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRegistrationWebDto {
    private AppointmentSlotWebDto appointmentSlotWebDto;
    private String clientUsername;
}
