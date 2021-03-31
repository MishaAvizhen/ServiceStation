package converters.impl;

import converters.Converter;
import dto.RepairRecordWebDto;
import entity.RepairRecord;
import org.springframework.stereotype.Component;

@Component
public class RepairRecordToRepairRecordWebDtoConverter implements Converter<RepairRecord, RepairRecordWebDto> {

    @Override
    public RepairRecordWebDto convertToDto(RepairRecord entity) {
        RepairRecordWebDto repairRecordWebDto = new RepairRecordWebDto();
        repairRecordWebDto.setRepairRecordId(entity.getId());
        repairRecordWebDto.setDetailPrice(entity.getDetailPrice());
        repairRecordWebDto.setOtherNotes(entity.getOtherNotes());
        repairRecordWebDto.setRepairRecordDescription(entity.getRepairRecordDescription());
        repairRecordWebDto.setWorkPrice(entity.getWorkPrice());
        repairRecordWebDto.setRepairRequest(entity.getRepairRequest());
        return repairRecordWebDto;

    }


    @Override
    public RepairRecord convertToEntity(RepairRecordWebDto dto) {
        return null;
    }
}
