package service.dto;

import java.util.Date;

public class WorkStartAndEndHoursDto {
   private int startWorkHour;
   private int endWorkHour;

    public WorkStartAndEndHoursDto(int startWorkHour, int endWorkHour) {
        this.startWorkHour = startWorkHour;
        this.endWorkHour = endWorkHour;
    }

    public int getStartWorkHour() {
        return startWorkHour;
    }

    public int getEndWorkHour() {
        return endWorkHour;
    }
}
