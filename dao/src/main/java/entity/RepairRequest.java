package entity;

import entity.consts.RepairRequestStatus;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "repair_request")
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class RepairRequest extends BaseEntity {
    @Column(name = "date_of_repair")
    private Date dateOfRequest;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RepairRequestStatus repairRequestStatus;
    @Column(name = "car_remark")
    private String carRemark;
    @Column(name = "repair_request_description")
    private String repairRequestDescription;
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;
    @OneToOne(cascade = {CascadeType.REFRESH}, mappedBy = "repairRequest", fetch = FetchType.EAGER)
    @ToString.Exclude
    private RepairRecord repairRecord;

}
