package dto;

import entity.consts.RepairRequestStatus;

import java.util.Date;

public class RepairRequestWebDto {
    private Long requestId;
    private Date dateOfRequest;
    private RepairRequestStatus repairRequestStatus;
    private String carRemark;
    private String repairRequestDescription;
    private String username;

    public RepairRequestWebDto() {
    }


}
