package controllers;

import converters.impl.RepairRecordFromDtoToUpdateToRegistrationDtoConverter;
import converters.impl.RepairRecordFromRegistrationWebDtoToRegistrationDtoConverter;
import dto.RepairRecordDtoToUpdate;
import dto.RepairRecordRegistrationWebDto;
import entity.RepairRecord;
import exceptions.NotContentException;
import exceptions.ResourceNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;
import service.RepairRecordService;
import service.dto.RepairRecordRegistrationDto;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/records")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved list"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
        @ApiResponse(code = 409, message = "The request could not be completed due to a conflict with the current state of the target resource."),
        @ApiResponse(code = 500, message = "Server ERROR. Something go wrong")
})
@Api(value = "Service station", description = "Repair record controller")
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
    @ApiOperation(value = "Get all repair records. Filter by username, carRemark")
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

    @GetMapping("/username")
    @ApiOperation(value = "Get all repair records of current user")
    public List<RepairRecord> repairRecordsOfUser(Principal principal) {
        String username = principal.getName();
        List<RepairRecord> repairRecordsOfUser = repairRecordService.findRepairRecordsByUsername(username);
        if (repairRecordsOfUser == null) {
            throw new ResourceNotFoundException("Records of user "+ username+ " not found");
        }
        return repairRecordsOfUser;
    }

    @DeleteMapping("/{recordId}")
    @ApiOperation(value = "Delete repair record")
    public void deleteRepairRecordById(@PathVariable Long recordId) {
        RepairRecord recordToDelete = repairRecordService.findRepairRecordById(recordId);
        if (recordToDelete == null) {
            throw new NotContentException(recordId.toString());
        } else {
            repairRecordService.deleteRepairRecordById(recordId);
        }
    }

    @PutMapping("/{recordId}")
    @ApiOperation(value = "Update repair record")
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
    @ApiOperation(value = "Create repair record")
    public RepairRecord getCreatedRepairRecord(@RequestBody RepairRecordRegistrationWebDto repairRecordRegistrationWebDto) {
        RepairRecordRegistrationDto repairRecordRegistrationDto =
                repairRecordFromRegistrationWebDtoToRegistrationDtoConverter.convertFromSourceDtoToTargetDto(repairRecordRegistrationWebDto);
        return repairRecordService.registerRepairRecord(repairRecordRegistrationDto);
    }
}
