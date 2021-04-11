package dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RepairRecordDtoToUpdate {
    private Long repairRecordId;
    private String repairRecordDescription;
    private Long detailPrice;
    private Long workPrice;
    private String otherNotes;
    private Long repairRequestId;
}
