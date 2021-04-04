package service;

import service.dto.AppointmentSlotDto;

import java.util.Date;
import java.util.List;

public interface AppointmentSlotService {

    List<AppointmentSlotDto> getAvailableAppointmentSlotsByDate(Date date);

    List<AppointmentSlotDto> getAvailableAppointmentSlotsByDates(Date start, Date end);
}
