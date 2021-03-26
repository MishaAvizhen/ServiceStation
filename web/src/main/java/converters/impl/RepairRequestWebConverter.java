package converters.impl;

import converters.Converter;
import dto.RepairRequestWebDto;
import entity.RepairRequest;

public class RepairRequestWebConverter implements Converter<RepairRequest, RepairRequestWebDto> {

    @Override
    public RepairRequestWebDto convertToDto(RepairRequest entity) {
        RepairRequestWebDto repairRequestWebDto = new RepairRequestWebDto();
        repairRequestWebDto.setUsername(entity.getUser().getUsername());
        repairRequestWebDto.setCarRemark(entity.getCarRemark());
        repairRequestWebDto.setDateOfRequest(entity.getDateOfRequest());
        repairRequestWebDto.setRepairRequestDescription(entity.getRepairRequestDescription());
        repairRequestWebDto.setRepairRequestStatus(entity.getRepairRequestStatus());
        return repairRequestWebDto;
    }
}
