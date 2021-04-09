package service;

import service.dto.AppointmentSlotDto;

import java.util.Date;
import java.util.List;

public interface AppointmentSlotService {

    List<AppointmentSlotDto> getAvailableAppointmentSlotsByDate(Date date);

    List<AppointmentSlotDto> getAllAppointmentSlotsByDates(Date start, Date end);

    boolean isAppointmentSlotAvailable(AppointmentSlotDto appointmentSlotDto);
}
