package controllers;

import converters.impl.RepairRequestFromRegistrationWebDtoToRegistrationDtoConverter;
import converters.impl.RepairRequestFromWebDtoToRegistrationDtoConverter;
import dto.RepairRequestRegistrationWebDto;
import dto.RepairRequestWebDto;
import entity.RepairRequest;
import entity.User;
import exceptions.ResourceNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.RepairRequestService;
import service.UserService;
import service.VacationService;
import service.dto.RepairRequestFilterDto;
import service.dto.RepairRequestRegistrationDto;
import service.dto.VacationRegistrationDto;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api/requests")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved list"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
        @ApiResponse(code = 409, message = "The request could not be completed due to a conflict with the current state of the target resource."),
        @ApiResponse(code = 500, message = "Server ERROR. Something go wrong")
})
@Api(tags = " Repair request controller", description = " Operations with repair request ")
public class RepairRequestRestController {
    private RepairRequestService repairRequestService;
    private UserService userService;
    private RepairRequestFromRegistrationWebDtoToRegistrationDtoConverter repairRequestFromRegistrationWebDtoToRegistrationDtoConverter;
    private RepairRequestFromWebDtoToRegistrationDtoConverter repairRequestFromWebDtoToRegistrationDtoConverter;
    private VacationService vacationService;

    @Autowired
    public RepairRequestRestController(RepairRequestService repairRequestService,
                                       UserService userService, RepairRequestFromRegistrationWebDtoToRegistrationDtoConverter repairRequestFromRegistrationWebDtoToRegistrationDtoConverter,
                                       RepairRequestFromWebDtoToRegistrationDtoConverter repairRequestFromWebDtoToRegistrationDtoConverter,
                                       VacationService vacationService) {
        this.repairRequestService = repairRequestService;
        this.userService = userService;
        this.repairRequestFromRegistrationWebDtoToRegistrationDtoConverter = repairRequestFromRegistrationWebDtoToRegistrationDtoConverter;
        this.repairRequestFromWebDtoToRegistrationDtoConverter = repairRequestFromWebDtoToRegistrationDtoConverter;
        this.vacationService = vacationService;
    }

    @GetMapping
    @ApiOperation(value = "Get all repair requests. Filter by username, carRemark, status")
    public List<RepairRequest> getAllRepairRequests(@RequestParam(value = "username", required = false) String username,
                                                    @RequestParam(value = "carRemark", required = false) String carRemark,
                                                    @RequestParam(value = "id", required = false) String id,
                                                    @RequestParam(value = "status", required = false) String status) {

        RepairRequestFilterDto repairRequestFilterDto = new RepairRequestFilterDto(id, username, carRemark, status);
        return repairRequestService.filterRepairRequest(repairRequestFilterDto);
    }

    @GetMapping("/profile")
    @ApiOperation(value = "Get all repair requests of current user")
    public List<RepairRequest> getAllRepairRequestsOfUser(Principal principal) {
        String username = principal.getName();
        List<RepairRequest> allRepairRequestsOfUser = repairRequestService.findAllRepairRequestsOfUser(username);
        if (allRepairRequestsOfUser == null) {
            throw new ResourceNotFoundException("Requests of user " + username + " not found");
        }
        return allRepairRequestsOfUser;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete repair request")
    public void deleteRepairRequestById(@PathVariable Long id) {
        repairRequestService.deleteRepairRequestById(id);

    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update repair request")
    public RepairRequest getUpdatedRepairRequest(@PathVariable Long id,
                                                 @RequestBody RepairRequestWebDto repairRequestWebDto) {
        RepairRequest repairRequestToUpdate = repairRequestService.findRepairRequestById(id);
        if (repairRequestToUpdate == null) {
            throw new ResourceNotFoundException("RepairRequest to update with id " + repairRequestWebDto.getRequestId() + " not found");
        } else {
            RepairRequestRegistrationDto repairRequestRegistrationDto =
                    repairRequestFromWebDtoToRegistrationDtoConverter.convertToServiceDto(repairRequestWebDto);
            return repairRequestService.updateRepairRequest(repairRequestRegistrationDto, repairRequestToUpdate);
        }
    }

    @PostMapping
    @ApiOperation(value = "Create repair request")
    public RepairRequest getCreatedRepairRequest(@RequestBody RepairRequestRegistrationWebDto repairRequestRegistrationWebDto) {
        String clientUsername = repairRequestRegistrationWebDto.getClientUsername();
        User client = userService.findUserByUsername(clientUsername);
        if (client == null) {
            throw new ResourceNotFoundException("Client with username " + clientUsername + " not found");
        }
        RepairRequestRegistrationDto repairRequestRegistrationDto =
                repairRequestFromRegistrationWebDtoToRegistrationDtoConverter.convertToServiceDto(repairRequestRegistrationWebDto);
        return repairRequestService.registerRepairRequest(repairRequestRegistrationDto);
    }

    @PostMapping("/vacation")
    @ApiOperation(value = "Create repair request for vacation for master")
    public RepairRequest getCreatedRepairRequestForVacation(@RequestBody VacationRegistrationDto vacationRegistrationDto) {
        // TODO перенсти в сервисы
        String masterName = vacationRegistrationDto.getMasterName();
        Date startDate = vacationRegistrationDto.getStartDate();
        Date endDate = vacationRegistrationDto.getEndDate();
        User master = userService.findUserByUsername(masterName);
        if (master == null) {
            throw new ResourceNotFoundException("Master with username " + masterName + " not found");
        }

        return vacationService.registerVacationRequest(masterName, startDate, endDate);
    }

}
