package controllers;

import converters.impl.AppointmentSlotDtoToAppointmentSlotWebDtoConverter;
import dto.AppointmentSlotWebDto;
import entity.Appointment;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.AppointmentService;
import service.AppointmentSlotService;
import service.dto.AppointmentSlotDto;

import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = "/api/appointments")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved list"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
        @ApiResponse(code = 409, message = "The request could not be completed due to a conflict with the current state of the target resource."),
        @ApiResponse(code = 500, message = "Server ERROR. Something go wrong")
})
@Api(tags = " Appointments controller", description = " Operations with appointments ")
public class AppointmentRestController {
    private AppointmentService appointmentService;
    private AppointmentSlotService appointmentSlotService;
    private AppointmentSlotDtoToAppointmentSlotWebDtoConverter appointmentSlotDtoToAppointmentSlotWebDtoConverter;

    @Autowired
    public AppointmentRestController(AppointmentService appointmentService, AppointmentSlotService appointmentSlotService,
                                     AppointmentSlotDtoToAppointmentSlotWebDtoConverter appointmentSlotDtoToAppointmentSlotWebDtoConverter) {
        this.appointmentService = appointmentService;
        this.appointmentSlotService = appointmentSlotService;
        this.appointmentSlotDtoToAppointmentSlotWebDtoConverter = appointmentSlotDtoToAppointmentSlotWebDtoConverter;
    }

    @GetMapping
    @ApiOperation(value = "Get all appointments")
    public List<Appointment> getAllAppointments() {
        return appointmentService.findAllAppointment();
    }

    @GetMapping("/slots")
    @ApiOperation(value = "Get available slots for repair request of some date")
    public List<AppointmentSlotWebDto> getAvailableSlotsOnTargetDate(@RequestParam(value = "targetDate")
                                                                     @DateTimeFormat(pattern = "yyyy-MM-dd") Date targetDate) {
        List<AppointmentSlotDto> availableAppointmentSlotsByDate = appointmentSlotService.getAvailableAppointmentSlotsByDate(targetDate);
        // TODO переписать на стрим
        return availableAppointmentSlotsByDate.stream()
                .map(appointmentSlotDto -> appointmentSlotDtoToAppointmentSlotWebDtoConverter.convertToServiceDto(appointmentSlotDto))
                .collect(toList());
    }
}
