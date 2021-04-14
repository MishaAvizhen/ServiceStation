package handler.impl;

import converters.impl.RepairRequestFromWebDtoToRegistrationDtoConverter;
import dto.RepairRequestWebDto;
import dto.errorHandling.ErrorDto;
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
import java.util.HashMap;
import java.util.Map;

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

        try {
            RepairRequestWebDto   repairRequestWebDto = readRequestEntity(RepairRequestWebDto.class, request);

            Long requestId = repairRequestWebDto.getRequestId();
            RepairRequest repairRequestById = repairRequestService.findRepairRequestById(requestId);
            if (repairRequestById != null) {
                RepairRequestRegistrationDto repairRequestRegistrationDto = repairRequestFromWebDtoToRegistrationDtoConverter.convertFromSourceDtoToTargetDto(repairRequestWebDto);

                RepairRequest repairRequestAfterUpdate = repairRequestService.updateRepairRequest(repairRequestRegistrationDto, repairRequestById);
                writeResponseAsJson(repairRequestAfterUpdate, response);
            } else {
                throw new UnsupportedOperationException("RepairRequest with id " + repairRequestById.getId() + " not found");

            }
        } catch (UnsupportedOperationException e) {
            ErrorDto errorDto = new ErrorDto(e.getMessage(), "admin@mail.ru");
            writeErrorResponseAsJson(errorDto, response, HttpServletResponse.SC_NOT_FOUND);
        } catch (RuntimeException e) {
            ErrorDto errorDto = new ErrorDto(e.getMessage(), "admin@mail.ru");
            writeErrorResponseAsJson(errorDto, response, HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            Map<String, String> errorDto = new HashMap<>();
            errorDto.put("message", e.getMessage());
            writeErrorResponseAsJson(errorDto, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String getHandledURI() {
        return "/api/repairRequests/updateRepairRequest";
    }
}
