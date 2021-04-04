package entity;


import entity.consts.SlotStatus;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = " appointment")
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Appointment extends BaseEntity {
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "client_id")
    private User client;
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "master_id")
    private User master;
    @Column(name = "slot_status")
    @Enumerated(EnumType.STRING)
    private SlotStatus slotStatus;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    @Column(name = "notes")
    private String notes;


}
