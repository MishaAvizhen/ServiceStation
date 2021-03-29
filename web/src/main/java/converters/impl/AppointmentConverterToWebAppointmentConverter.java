package converters.impl;

import converters.ConverterFromFirstDtoToSecond;
import dto.AppointmentSlotWebDto;
import service.common.LocalDateTimeOperations;
import service.dto.AppointmentSlotDto;

import java.util.Date;

public class AppointmentConverterToWebAppointmentConverter implements ConverterFromFirstDtoToSecond<AppointmentSlotDto, AppointmentSlotWebDto> {

    @Override
    public AppointmentSlotWebDto convertFromSourceDtoToTargetDto(AppointmentSlotDto sourceDto) {
        Date startDate = LocalDateTimeOperations.convertLocalDateTimeToDate(sourceDto.getStartDate());
        Date endDate = LocalDateTimeOperations.convertLocalDateTimeToDate(sourceDto.getEndDate());

        AppointmentSlotWebDto appointmentSlotWebDto = new AppointmentSlotWebDto();
        appointmentSlotWebDto.setMaster(sourceDto.getMaster());
        appointmentSlotWebDto.setStartDate(startDate);
        appointmentSlotWebDto.setEndDate(endDate);
        return appointmentSlotWebDto;
    }
}
