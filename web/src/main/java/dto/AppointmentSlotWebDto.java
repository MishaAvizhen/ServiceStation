package dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import entity.User;
import lombok.*;

import java.time.ZoneId;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentSlotWebDto {
    private User master;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'",
            timezone = JsonFormat.DEFAULT_TIMEZONE)
    private Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'",
            timezone = JsonFormat.DEFAULT_TIMEZONE)
    private Date endDate;
}
