package converters.impl;

import converters.Converter;
import dto.AppointmentSlotWebDto;
import org.springframework.stereotype.Component;
import service.common.LocalDateTimeOperations;
import service.dto.AppointmentSlotDto;

import java.util.Date;
@Component
public class AppointmentSlotWebConverter implements Converter<AppointmentSlotDto, AppointmentSlotWebDto> {

    @Override
    public AppointmentSlotWebDto convertToServiceDto(AppointmentSlotDto webDto) {
        Date startDate = LocalDateTimeOperations.convertLocalDateTimeToDate(webDto.getStartDate());
        Date endDate = LocalDateTimeOperations.convertLocalDateTimeToDate(webDto.getEndDate());

        AppointmentSlotWebDto appointmentSlotWebDto = new AppointmentSlotWebDto();
        appointmentSlotWebDto.setMaster(webDto.getMaster());
        appointmentSlotWebDto.setStartDate(startDate);
        appointmentSlotWebDto.setEndDate(endDate);
        return appointmentSlotWebDto;
    }

    @Override
    public AppointmentSlotDto convertToWebDto(AppointmentSlotWebDto serviceDto) {
        AppointmentSlotDto appointmentSlotDto = new AppointmentSlotDto();
        appointmentSlotDto.setMaster(serviceDto.getMaster());
        appointmentSlotDto.setStartDate(LocalDateTimeOperations.convertDateToLocalDateTime(serviceDto.getStartDate()));
        appointmentSlotDto.setEndDate(LocalDateTimeOperations.convertDateToLocalDateTime(serviceDto.getEndDate()));
        return appointmentSlotDto;
    }
}
