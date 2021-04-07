package controllers;

import converters.impl.RepairRecordFromDtoToUpdateToRegistrationDtoConverter;
import converters.impl.RepairRecordFromRegistrationWebDtoToRegistrationDtoConverter;
import dto.RepairRecordDtoToUpdate;
import dto.RepairRecordRegistrationWebDto;
import entity.RepairRecord;
import exceptions.NotContentException;
import exceptions.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;
import service.RepairRecordService;
import service.dto.RepairRecordRegistrationDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/records")
public class RepairRecordRestController {
    private RepairRecordService repairRecordService;
    private RepairRecordFromRegistrationWebDtoToRegistrationDtoConverter repairRecordFromRegistrationWebDtoToRegistrationDtoConverter;
    private RepairRecordFromDtoToUpdateToRegistrationDtoConverter repairRecordFromDtoToUpdateToRegistrationDtoConverter;

    public RepairRecordRestController(RepairRecordService repairRecordService, RepairRecordFromRegistrationWebDtoToRegistrationDtoConverter repairRecordFromRegistrationWebDtoToRegistrationDtoConverter, RepairRecordFromDtoToUpdateToRegistrationDtoConverter repairRecordFromDtoToUpdateToRegistrationDtoConverter) {
        this.repairRecordService = repairRecordService;
        this.repairRecordFromRegistrationWebDtoToRegistrationDtoConverter = repairRecordFromRegistrationWebDtoToRegistrationDtoConverter;
        this.repairRecordFromDtoToUpdateToRegistrationDtoConverter = repairRecordFromDtoToUpdateToRegistrationDtoConverter;
    }

    @GetMapping
    public List<RepairRecord> getAllRepairRecords(@RequestParam(value = "username", required = false) String username,
                                                  @RequestParam(value = "carRemark", required = false) String carRemark,
                                                  @RequestParam(value = "recordId", required = false) String repairRecordId) {

        List<RepairRecord> allRepairRecords = repairRecordService.findAllRepairRecords();
        return allRepairRecords.stream()
                .filter(repairRecord -> username == null || username.equals(repairRecord.getRepairRequest().getUser().getUsername()))
                .filter(repairRecord -> carRemark == null || carRemark.equals(repairRecord.getRepairRequest().getCarRemark()))
                .filter(repairRecord -> repairRecordId == null || repairRecordId.equals(repairRecord.getId().toString()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{recordId}")
    public RepairRecord getRepairRecord(@PathVariable Long recordId) {
        RepairRecord foundRecord = repairRecordService.findRepairRecordById(recordId);
        if (foundRecord == null) {
            throw new ResourceNotFoundException(recordId.toString());
        }
        return repairRecordService.findRepairRecordById(recordId);
    }

    @DeleteMapping("/{recordId}")
    public void deleteRepairRecordById(@PathVariable Long recordId) {
        RepairRecord recordToDelete = repairRecordService.findRepairRecordById(recordId);
        if (recordToDelete == null) {
            throw new NotContentException(recordId.toString());
        } else {
            repairRecordService.deleteRepairRecordById(recordId);
        }
    }

    @PutMapping("/{recordId}")
    public RepairRecord getUpdatedRepairRecord(@RequestBody RepairRecordDtoToUpdate repairRecordDtoToUpdate,
                                               @PathVariable Long recordId) {
        RepairRecord repairRecordToUpdate = repairRecordService.findRepairRecordById(recordId);
        if (repairRecordToUpdate == null) {
            throw new ResourceNotFoundException("RepairRecord to update with id " + repairRecordDtoToUpdate.getRepairRecordId() + " not found");
        } else {
            RepairRecordRegistrationDto repairRecordRegistrationDto =
                    repairRecordFromDtoToUpdateToRegistrationDtoConverter.convertFromSourceDtoToTargetDto(repairRecordDtoToUpdate);
            return repairRecordService.updateRepairRecord(repairRecordRegistrationDto, repairRecordToUpdate);
        }
    }

    @PostMapping("/create")
    public RepairRecord getCreatedRepairRecord(@RequestBody RepairRecordRegistrationWebDto repairRecordRegistrationWebDto) {
        RepairRecordRegistrationDto repairRecordRegistrationDto =
                repairRecordFromRegistrationWebDtoToRegistrationDtoConverter.convertFromSourceDtoToTargetDto(repairRecordRegistrationWebDto);
        return repairRecordService.registerRepairRecord(repairRecordRegistrationDto);
    }
}
