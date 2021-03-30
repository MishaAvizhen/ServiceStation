package converters.impl;

import converters.ConverterFromFirstDtoToSecond;
import dto.RepairRequestRegistrationWebDto;
import entity.consts.RepairRequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.dto.AppointmentSlotDto;
import service.dto.RepairRequestRegistrationDto;

import java.util.Date;

@Component
public class RepairRequestFromWebDtoToRegistrationDtoConverter implements
        ConverterFromFirstDtoToSecond<RepairRequestRegistrationWebDto, RepairRequestRegistrationDto> {

    private AppointmentSlotWebDtoToAppointmentSlotDtoConverter appointmentSlotWebDtoToAppointmentSlotDtoConverter;

    @Autowired
    public RepairRequestFromWebDtoToRegistrationDtoConverter(AppointmentSlotWebDtoToAppointmentSlotDtoConverter appointmentSlotWebDtoToAppointmentSlotDtoConverter) {
        this.appointmentSlotWebDtoToAppointmentSlotDtoConverter = appointmentSlotWebDtoToAppointmentSlotDtoConverter;
    }

    @Override
    public RepairRequestRegistrationDto convertFromSourceDtoToTargetDto(RepairRequestRegistrationWebDto sourceDto) {
        AppointmentSlotDto appointmentSlotDto = appointmentSlotWebDtoToAppointmentSlotDtoConverter
                .convertFromSourceDtoToTargetDto(sourceDto.getAppointmentSlotWebDto());
        return new RepairRequestRegistrationDto.Builder()
                .setDateOfRequest(new Date())
                .setRepairRequestDescription(sourceDto.getRepairRequestDescription())
                .setRepairRequestStatus(RepairRequestStatus.IN_PROGRESS)
                .setCarRemark(sourceDto.getCarRemark())
                .setUsername(sourceDto.getClientUsername())
                .setAppointmentSlotDto(appointmentSlotDto)
                .build();

    }
}
