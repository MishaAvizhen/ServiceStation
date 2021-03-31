package dto;

import entity.RepairRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class RepairRecordWebDto {

    private Long repairRecordId;
    private String repairRecordDescription;
    private Long detailPrice;
    private Long workPrice;
    private String otherNotes;
    private RepairRequest repairRequest;

}
