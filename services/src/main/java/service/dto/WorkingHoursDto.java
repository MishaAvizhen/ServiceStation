package service.dto;

import java.util.Date;

public class WorkingHoursDto {
   private int startWorkHour;
   private int endWorkHour;

    public WorkingHoursDto(int startWorkHour, int endWorkHour) {
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
