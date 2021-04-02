package converters.impl;

import converters.ConverterFromFirstDtoToSecond;
import dto.RepairRequestWebDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.dto.RepairRequestRegistrationDto;

@Component
public class RepairRequestFromWebDtoToRegistrationDtoConverter implements
        ConverterFromFirstDtoToSecond<RepairRequestWebDto,RepairRequestRegistrationDto> {

    private AppointmentSlotWebDtoToAppointmentSlotDtoConverter slotDtoConverter;

    @Autowired
    public RepairRequestFromWebDtoToRegistrationDtoConverter(AppointmentSlotWebDtoToAppointmentSlotDtoConverter slotDtoConverter) {
        this.slotDtoConverter = slotDtoConverter;
    }

    @Override
    public RepairRequestRegistrationDto convertFromSourceDtoToTargetDto(RepairRequestWebDto sourceDto) {
        return new RepairRequestRegistrationDto.Builder()
                .setRepairRequestStatus(sourceDto.getRepairRequestStatus())
                .setCarRemark(sourceDto.getCarRemark())
                .setUsername(sourceDto.getUsername())
                .setRepairRequestDescription(sourceDto.getRepairRequestDescription())
                .setDateOfRequest(sourceDto.getDateOfRequest())
                .setAppointmentSlotDto(slotDtoConverter.convertFromSourceDtoToTargetDto(sourceDto.getAppointmentSlotWebDto()))
                .build();
    }
}
