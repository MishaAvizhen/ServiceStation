package converters.impl;

import converters.ConverterFromFirstDtoToSecond;
import dto.RepairRequestWebDto;
import service.dto.RepairRequestRegistrationDto;

public class RepairRequestConverterFromWebDtoToRegistrationDto implements
        ConverterFromFirstDtoToSecond<RepairRequestWebDto, RepairRequestRegistrationDto> {

    @Override
    public RepairRequestRegistrationDto convertFromSourceDtoToTargetDto(RepairRequestWebDto webDto) {
        return new RepairRequestRegistrationDto.Builder()
                .setUsername(webDto.getUsername())
                .setRepairRequestDescription(webDto.getRepairRequestDescription())
                .setRepairRequestStatus(webDto.getRepairRequestStatus())
                .setCarRemark(webDto.getCarRemark())
                .setDateOfRequest(webDto.getDateOfRequest())
                .build();
    }
}
