package service.dto;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentFilterDto {
    private String masterName;
    private String username;
    private String status;
}
