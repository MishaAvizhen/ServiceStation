package dto;

import entity.RepairRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Repair record entity for web registration")
public class RepairRecordRegistrationWebDto {

    private String repairRecordDescription;
    private Long detailPrice;
    private Long workPrice;
    private String otherNotes;
    private Long repairRequestId;
}
