package dto;

import entity.RepairRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Repair record entity for web")
public class RepairRecordWebDto {

    private Long repairRecordId;
    private String repairRecordDescription;
    private Long detailPrice;
    private Long workPrice;
    private String otherNotes;
    private RepairRequest repairRequest;

}
