package dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
