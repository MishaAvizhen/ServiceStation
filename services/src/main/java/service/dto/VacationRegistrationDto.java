package service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Vacation entity for master")
public class VacationRegistrationDto {
    private String masterName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",
            timezone = JsonFormat.DEFAULT_TIMEZONE)
    private Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",
            timezone = JsonFormat.DEFAULT_TIMEZONE)
    private Date endDate;


}
