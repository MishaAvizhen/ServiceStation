package dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Repair record entity for web to update")
public class RepairRecordDtoToUpdate {
    private Long repairRecordId;
    private String repairRecordDescription;
    private Long detailPrice;
    private Long workPrice;
    private String otherNotes;
    private Long repairRequestId;
}
