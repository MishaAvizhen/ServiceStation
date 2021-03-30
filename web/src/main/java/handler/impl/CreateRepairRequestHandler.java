package handler.impl;

import converters.impl.RepairRequestFromRegistrationWebDtoToRegistrationDtoConverter;
import converters.impl.RepairRequestToRepairRequestWebDtoConverter;
import dto.RepairRequestRegistrationWebDto;
import entity.RepairRequest;
import handler.StoRestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.RepairRequestService;
import service.dto.RepairRequestRegistrationDto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CreateRepairRequestHandler extends StoRestHandler {

    private RepairRequestService repairRequestService;
    private RepairRequestFromRegistrationWebDtoToRegistrationDtoConverter repairRequestFromRegistrationWebDtoToRegistrationDtoConverter;
    private RepairRequestToRepairRequestWebDtoConverter requestWebConverter;

    @Autowired
    public CreateRepairRequestHandler(RepairRequestService repairRequestService,
                                      RepairRequestFromRegistrationWebDtoToRegistrationDtoConverter repairRequestFromRegistrationWebDtoToRegistrationDtoConverter,
                                      RepairRequestToRepairRequestWebDtoConverter requestWebConverter) {
        this.repairRequestService = repairRequestService;
        this.repairRequestFromRegistrationWebDtoToRegistrationDtoConverter = repairRequestFromRegistrationWebDtoToRegistrationDtoConverter;
        this.requestWebConverter = requestWebConverter;
    }

    @Override
    public void handleDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RepairRequestRegistrationWebDto repairRequestRegistrationWebDto =
                readRequestEntity(RepairRequestRegistrationWebDto.class, request);

        RepairRequestRegistrationDto repairRequestRegistrationDto =
                repairRequestFromRegistrationWebDtoToRegistrationDtoConverter.convertFromSourceDtoToTargetDto(repairRequestRegistrationWebDto);

        RepairRequest repairRequest = repairRequestService.registerRepairRequest(repairRequestRegistrationDto);

        writeResponseAsJson(requestWebConverter.convertToDto(repairRequest), response);

    }

    @Override
    public String getHandledURI() {
        return "/api/createRepairRequest";
    }
}
