package handler.impl;

import converters.impl.RepairRequestToRepairRequestWebDtoConverter;
import dto.RepairRequestWebDto;
import entity.RepairRequest;
import entity.consts.RepairRequestStatus;
import handler.StoRestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.RepairRequestService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindAllRepairRequestsHandler extends StoRestHandler {
    private RepairRequestService repairRequestService;
    private RepairRequestToRepairRequestWebDtoConverter requestWebConverter;

    @Autowired
    public FindAllRepairRequestsHandler(RepairRequestService repairRequestService, RepairRequestToRepairRequestWebDtoConverter requestWebConverter) {
        this.repairRequestService = repairRequestService;
        this.requestWebConverter = requestWebConverter;
    }


    @Override
    public void handleDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String carRemark = request.getParameter("carRemark");
        Long requestId = request.getParameter("requestId") != null ? Long.valueOf(request.getParameter("requestId")) : null;
        List<RepairRequest> allRepairRequests = repairRequestService.findAllRepairRequests();
        List<RepairRequest> filteredRequest = allRepairRequests.stream()
                .filter(repairRequest -> username == null || username.equals(repairRequest.getUser().getUsername()))
                .filter(repairRequest -> carRemark == null || carRemark.equals(repairRequest.getCarRemark()))
                .filter(repairRequest -> requestId == null || requestId.equals(repairRequest.getId()))
                .collect(Collectors.toList());
        List<RepairRequestWebDto> requestWebDtos = new ArrayList<>();
        for (RepairRequest repairRequest : filteredRequest) {
            RepairRequestWebDto repairRequestWebDto = requestWebConverter.convertToDto(repairRequest);
            requestWebDtos.add(repairRequestWebDto);

        }
        writeResponseAsJson(requestWebDtos, response);
    }

    @Override
    public String getHandledURI() {
        return "/api/repairRequests";
    }
}
