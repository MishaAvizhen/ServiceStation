package handler.impl;

import converters.impl.RepairRequestFromWebDtoToRegistrationDtoConverter;
import dto.RepairRequestWebDto;
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
public class UpdateRepairRequestHandler extends StoRestHandler {

    private RepairRequestService repairRequestService;
    private RepairRequestFromWebDtoToRegistrationDtoConverter repairRequestFromWebDtoToRegistrationDtoConverter;

    @Autowired
    public UpdateRepairRequestHandler(RepairRequestService repairRequestService,
                                      RepairRequestFromWebDtoToRegistrationDtoConverter repairRequestFromWebDtoToRegistrationDtoConverter) {
        this.repairRequestService = repairRequestService;
        this.repairRequestFromWebDtoToRegistrationDtoConverter = repairRequestFromWebDtoToRegistrationDtoConverter;
    }

    @Override
    public void handleDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RepairRequestWebDto repairRequestWebDto = readRequestEntity(RepairRequestWebDto.class, request);
        Long requestId = repairRequestWebDto.getRequestId();
        RepairRequest repairRequestById = repairRequestService.findRepairRequestById(requestId);
        if (repairRequestById != null) {
            RepairRequestRegistrationDto repairRequestRegistrationDto = repairRequestFromWebDtoToRegistrationDtoConverter.convertFromSourceDtoToTargetDto(repairRequestWebDto);

            RepairRequest repairRequestAfterUpdate = repairRequestService.updateRepairRequest(repairRequestRegistrationDto, repairRequestById);
            writeResponseAsJson(repairRequestAfterUpdate, response);
        }
    }

    @Override
    public String getHandledURI() {
        return "/api/repairRequests/updateRepairRequest";
    }
}
