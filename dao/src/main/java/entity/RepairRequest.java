package entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import entity.consts.RepairRequestStatus;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "repair_request")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RepairRequest extends BaseEntity {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
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
    private User user;

    @OneToOne(cascade = {CascadeType.REFRESH}, mappedBy = "repairRequest", fetch = FetchType.EAGER)
    private RepairRecord repairRecord;

    @OneToMany(cascade = {CascadeType.REFRESH}, mappedBy = "repairRequest", fetch = FetchType.EAGER)
    private List<Appointment> appointments;

    @Override
    public String toString() {
        return "RepairRequest{" +
                "dateOfRequest=" + dateOfRequest +
                ", repairRequestStatus=" + repairRequestStatus +
                ", carRemark='" + carRemark + '\'' +
                ", repairRequestDescription='" + repairRequestDescription + '\'' +
                ", userId=" + user.getId() +
                ", username=" + user.getUsername() +
                "} " + super.toString();
    }

}
