package service.converters.impl;

import entity.RepairRecord;
import entity.RepairRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.RepairRequestRepository;
import service.converters.Converter;
import service.dto.RepairRecordRegistrationDto;

import java.util.Optional;

@Component
public class RepairRecordConverter implements Converter<RepairRecord, RepairRecordRegistrationDto> {

    private RepairRequestRepository repairRequestRepository;

    @Autowired
    public RepairRecordConverter(RepairRequestRepository repairRequestRepository) {
        this.repairRequestRepository = repairRequestRepository;
    }

    @Override
    public RepairRecord convertToEntity(RepairRecordRegistrationDto dto) {

        RepairRecord repairRecord = new RepairRecord();

        return convertToExistingEntity(dto, repairRecord);
    }

    @Override
    public RepairRecord convertToExistingEntity(RepairRecordRegistrationDto dto, RepairRecord entity) {
        Optional<RepairRequest> requestOptional = repairRequestRepository.findById(dto.getRepairRequestId());
        RepairRequest repairRequest = requestOptional.orElse(null);
        entity.setRepairRequest(repairRequest);
        entity.setOtherNotes(dto.getOtherNotes());
        entity.setRepairRecordDescription(dto.getRepairRecordDescription());
        entity.setDetailPrice(dto.getDetailPrice());
        entity.setWorkPrice(dto.getWorkPrice());
        return entity;
    }

    @Override
    public RepairRecordRegistrationDto convertToDto(RepairRecord entity) {
        return new RepairRecordRegistrationDto.Builder()
                .setRepairRequest(entity.getRepairRequest().getId())
                .setOtherNotes(entity.getOtherNotes())
                .setRepairRecordDescription(entity.getRepairRecordDescription())
                .setWorkPrice(entity.getWorkPrice())
                .setDetailPrice(entity.getDetailPrice())
                .build();
    }
}
