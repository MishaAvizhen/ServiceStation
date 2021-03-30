package converters.impl;

import converters.ConverterFromFirstDtoToSecond;
import dto.RepairRequestWebDto;
import entity.Appointment;
import org.springframework.stereotype.Component;
import service.AppointmentService;
import service.dto.RepairRequestRegistrationDto;

@Component
public class RepairRequestFromWebDtoToRegistrationDtoConverter implements
        ConverterFromFirstDtoToSecond<RepairRequestWebDto,RepairRequestRegistrationDto> {

    @Override
    public RepairRequestRegistrationDto convertFromSourceDtoToTargetDto(RepairRequestWebDto sourceDto) {
        return new RepairRequestRegistrationDto.Builder()
                .setRepairRequestStatus(sourceDto.getRepairRequestStatus())
                .setCarRemark(sourceDto.getCarRemark())
                .setUsername(sourceDto.getUsername())
                .setRepairRequestDescription(sourceDto.getRepairRequestDescription())
                .setDateOfRequest(sourceDto.getDateOfRequest())
                .setAppointmentSlotDto(sourceDto.getAppointmentSlotDto())
                .build();
    }
}
