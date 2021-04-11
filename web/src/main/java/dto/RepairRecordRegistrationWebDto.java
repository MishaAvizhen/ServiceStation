package dto;

import entity.RepairRequest;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RepairRecordRegistrationWebDto {
    private String repairRecordDescription;
    private Long detailPrice;
    private Long workPrice;
    private String otherNotes;
    private Long repairRequestId;
}
