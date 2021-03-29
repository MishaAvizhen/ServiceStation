package converters.impl;

import converters.ConverterFromFirstDtoToSecond;
import dto.AppointmentSlotWebDto;
import org.springframework.stereotype.Component;
import service.common.LocalDateTimeOperations;
import service.dto.AppointmentSlotDto;

@Component
public class AppointmentSlotWebDtoToAppointmentSlotDto
        implements ConverterFromFirstDtoToSecond<AppointmentSlotWebDto, AppointmentSlotDto> {
    @Override
    public AppointmentSlotDto convertFromSourceDtoToTargetDto(AppointmentSlotWebDto sourceDto) {
        AppointmentSlotDto appointmentSlotDto = new AppointmentSlotDto();
        appointmentSlotDto.setMaster(sourceDto.getMaster());
        appointmentSlotDto.setStartDate(LocalDateTimeOperations.convertDateToLocalDateTime(sourceDto.getStartDate()));
        appointmentSlotDto.setEndDate(LocalDateTimeOperations.convertDateToLocalDateTime(sourceDto.getEndDate()));
        return appointmentSlotDto;
    }
}
