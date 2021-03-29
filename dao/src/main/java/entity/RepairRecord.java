package entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = " repair_record")
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class RepairRecord extends BaseEntity {
    @Column(name = "repair_record_description")
    private String repairRecordDescription;
    @Column(name = "detail_price")
    private Long detailPrice;
    @Column(name = "work_price")
    private Long workPrice;
    @Column(name = "other_notes")
    private String otherNotes;

    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "repair_request_id")
    @ToString.Exclude
    private RepairRequest repairRequest;


}
