package service.dto;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class RepairRequestFilterDto {
    private String id;
    private String username;
    private String CarRemark;
    private String status;

}
