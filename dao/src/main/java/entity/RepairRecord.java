package entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = " repair_record")
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class RepairRecord extends BaseEntity {
    @Column(name = "repair_record_description")
    private String repairRecordDescription;
    @ApiModelProperty(notes = "Detail price, BYN")
    @Column(name = "detail_price")
    private Long detailPrice;
    @ApiModelProperty(notes = "Work price, BYN")
    @Column(name = "work_price")
    private Long workPrice;
    @Column(name = "other_notes")
    private String otherNotes;
    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "repair_request_id")
    @ToString.Exclude
    private RepairRequest repairRequest;


}
