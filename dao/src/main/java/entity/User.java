package entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import entity.consts.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@ApiModel
public class User extends BaseEntity {
    @ApiModelProperty(notes = "Username of user")
    @Column(name = "username")
    private String username;
    @ApiModelProperty(notes = "Password of user")
    @Column(name = "password")
    private String password;
    @ApiModelProperty(notes = "Phone number of user")
    @Column(name = "phone_number")
    private String phoneNumber;
    @ApiModelProperty(notes = "Email of user")
    @Column(name = "email")
    private String email;
    @ApiModelProperty(notes = "Role of user")
    @Column(name = "user_roles")
    @Enumerated(EnumType.STRING)
    private Role role;
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @ToString.Exclude private List<RepairRequest> repairRequestList;
    @JsonIgnore
    @OneToMany(mappedBy = "client", cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @ToString.Exclude private Set<Appointment> clientAppointmentList;
    @JsonIgnore
    @OneToMany(mappedBy = "master", cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @ToString.Exclude private Set<Appointment> masterAppointmentList;

}
