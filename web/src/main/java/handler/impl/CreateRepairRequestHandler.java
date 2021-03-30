package handler.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import converters.impl.RepairRequestFromWebDtoToRegistrationDtoConverter;
import converters.impl.RepairRequestToRepairRequestWebDtoConverter;
import dto.RepairRequestRegistrationWebDto;
import entity.RepairRequest;
import handler.StoHandlerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.RepairRequestService;
import service.dto.RepairRequestRegistrationDto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CreateRepairRequestHandler extends StoHandlerAdapter {

    private RepairRequestService repairRequestService;
    private RepairRequestFromWebDtoToRegistrationDtoConverter repairRequestFromWebDtoToRegistrationDtoConverter;
    private RepairRequestToRepairRequestWebDtoConverter requestWebConverter;
    private ObjectMapper objectMapper;

    @Autowired
    public CreateRepairRequestHandler(RepairRequestService repairRequestService,
                                      RepairRequestFromWebDtoToRegistrationDtoConverter repairRequestFromWebDtoToRegistrationDtoConverter,
                                      RepairRequestToRepairRequestWebDtoConverter requestWebConverter,
                                      ObjectMapper objectMapper) {
        this.repairRequestService = repairRequestService;
        this.repairRequestFromWebDtoToRegistrationDtoConverter = repairRequestFromWebDtoToRegistrationDtoConverter;
        this.requestWebConverter = requestWebConverter;
        this.objectMapper = objectMapper;
    }

    @Override
    public void handleDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RepairRequestRegistrationWebDto repairRequestRegistrationWebDto = objectMapper.readValue(request.getInputStream(),
                RepairRequestRegistrationWebDto.class);
        RepairRequestRegistrationDto repairRequestRegistrationDto = repairRequestFromWebDtoToRegistrationDtoConverter.convertFromSourceDtoToTargetDto(repairRequestRegistrationWebDto);


        RepairRequest repairRequest = repairRequestService.registerRepairRequest(repairRequestRegistrationDto);

        PrintWriter out = response.getWriter();
        String jsonString = objectMapper.writeValueAsString(requestWebConverter.convertToDto(repairRequest));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(jsonString);
        out.flush();
    }

    @Override
    public String getHandledURI() {
        return "/api/createRepairRequest";
    }
}
