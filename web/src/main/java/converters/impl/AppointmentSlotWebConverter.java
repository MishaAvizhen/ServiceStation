package converters.impl;

import converters.Converter;
import dto.AppointmentSlotWebDto;
import org.springframework.stereotype.Component;
import service.dto.AppointmentSlotDto;

import java.util.Date;

import static service.common.LocalDateTimeOperations.convertDateToLocalDateTime;
import static service.common.LocalDateTimeOperations.convertLocalDateTimeToDate;

@Component
public class AppointmentSlotWebConverter implements Converter<AppointmentSlotDto, AppointmentSlotWebDto> {

    @Override
    public AppointmentSlotWebDto convertToServiceDto(AppointmentSlotDto webDto) {
        Date startDate = convertLocalDateTimeToDate(webDto.getStartDate());
        Date endDate = convertLocalDateTimeToDate(webDto.getEndDate());

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
        appointmentSlotDto.setStartDate(convertDateToLocalDateTime(serviceDto.getStartDate()));
        appointmentSlotDto.setEndDate(convertDateToLocalDateTime(serviceDto.getEndDate()));
        return appointmentSlotDto;
    }
}
