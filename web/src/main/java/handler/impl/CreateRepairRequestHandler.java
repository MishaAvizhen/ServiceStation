package handler.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import converters.impl.RepairRequestConverterFromWebDtoToRegistrationDto;
import converters.impl.RepairRequestWebConverter;
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
    private RepairRequestConverterFromWebDtoToRegistrationDto repairRequestConverterFromWebDtoToRegistrationDto;
    private RepairRequestWebConverter requestWebConverter;

    @Autowired
    public CreateRepairRequestHandler(RepairRequestService repairRequestService,
                                      RepairRequestConverterFromWebDtoToRegistrationDto repairRequestConverterFromWebDtoToRegistrationDto,
                                      RepairRequestWebConverter requestWebConverter) {
        this.repairRequestService = repairRequestService;
        this.repairRequestConverterFromWebDtoToRegistrationDto = repairRequestConverterFromWebDtoToRegistrationDto;
        this.requestWebConverter = requestWebConverter;
    }

    @Override
    public void handleDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        RepairRequestRegistrationWebDto repairRequestRegistrationWebDto = mapper.readValue(request.getInputStream(),
                RepairRequestRegistrationWebDto.class);
        RepairRequestRegistrationDto repairRequestRegistrationDto = repairRequestConverterFromWebDtoToRegistrationDto.convertFromSourceDtoToTargetDto(repairRequestRegistrationWebDto);


        RepairRequest repairRequest = repairRequestService.registerRepairRequest(repairRequestRegistrationDto);

        PrintWriter out = response.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
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
