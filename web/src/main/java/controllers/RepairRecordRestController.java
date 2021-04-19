package controllers;

import converters.impl.RepairRecordWebConverter;
import dto.RepairRecordRegistrationWebDto;
import entity.RepairRecord;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;
import service.RepairRecordService;
import service.dto.RepairRecordFilterDto;
import service.dto.RepairRecordRegistrationDto;

import java.security.Principal;
import java.util.List;

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
@Api(tags = " Repair record controller", description = " Operations with repair record ")
public class RepairRecordRestController {
    private RepairRecordService repairRecordService;
    private RepairRecordWebConverter repairRecordWebConverter;

    public RepairRecordRestController(RepairRecordService repairRecordService,
                                      RepairRecordWebConverter repairRecordWebConverter) {
        this.repairRecordService = repairRecordService;
        this.repairRecordWebConverter = repairRecordWebConverter;
    }

    @GetMapping
    @ApiOperation(value = "Get all repair records. Filter by username, masterName, carRemark, id")
    public List<RepairRecord> getAllRepairRecords(@RequestParam(value = "username", required = false) String username,
                                                  @RequestParam(value = "carRemark", required = false) String carRemark,
                                                  @RequestParam(value = "id", required = false) String id) {
        RepairRecordFilterDto filterDto = new RepairRecordFilterDto(id, username, carRemark);
        // TODO перенести в сервисы, обернуть параметры в Dto
        return repairRecordService.filterRepairRecord(filterDto);
    }

    // TODO переименовать username -> my/profile ...
    @GetMapping("/profile")
    @ApiOperation(value = "Get all repair records of current user")
    public List<RepairRecord> repairRecordsOfUser(Principal principal) {
        String username = principal.getName();
        // TODO apache common - CollectionUtils.isNotEmpty()
        return repairRecordService.findRepairRecordsByUsername(username);
    }

    // TODO упростить до id
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete repair record")
    public void deleteById(@PathVariable Long id) {
        // TODO аналогично убрать дублирование
        repairRecordService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update repair record")
    public RepairRecord getUpdatedRepairRecord(@RequestBody RepairRecordRegistrationWebDto repairRecordRegistrationWebDto,
                                               @PathVariable Long id) {
        // TODO перенести в сервис
        RepairRecordRegistrationDto repairRecordRegistrationDto =
                repairRecordWebConverter.convertToServiceDto(repairRecordRegistrationWebDto);
        return repairRecordService.updateRepairRecord(repairRecordRegistrationDto, id);
    }

    // TODO create излишен
    @PostMapping
    @ApiOperation(value = "Create repair record")
    public RepairRecord getCreatedRepairRecord(@RequestBody RepairRecordRegistrationWebDto repairRecordRegistrationWebDto) {
        RepairRecordRegistrationDto repairRecordRegistrationDto =
                repairRecordWebConverter.convertToServiceDto(repairRecordRegistrationWebDto);
        return repairRecordService.registerRepairRecord(repairRecordRegistrationDto);
    }

}
