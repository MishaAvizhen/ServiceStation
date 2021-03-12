package entity;


import entity.constants.Role;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "email")
    private String email;

    @Column(name = "user_roles")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private List<RepairRequest> repairRequestList;

    @OneToMany(mappedBy = "client", cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private List<Appointment> clientAppointmentList;

    @OneToMany(mappedBy = "master", cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private List<Appointment> masterAppointmentList;

    public User() {
    }

    public User(String username, String password, String phoneNumber, String email, Role role, List<RepairRequest> repairRequestList,
                List<Appointment> clientAppointmentList, List<Appointment> masterAppointmentList) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.role = role;
        this.repairRequestList = repairRequestList;
        this.clientAppointmentList = clientAppointmentList;
        this.masterAppointmentList = masterAppointmentList;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public User setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public User setRole(Role role) {
        this.role = role;
        return this;
    }

    public List<RepairRequest> getRepairRequestList() {
        return repairRequestList;
    }

    public User setRepairRequestList(List<RepairRequest> repairRequestList) {
        this.repairRequestList = repairRequestList;
        return this;
    }

    public List<Appointment> getClientAppointmentList() {
        return clientAppointmentList;
    }

    public User setClientAppointmentList(List<Appointment> clientAppointmentList) {
        this.clientAppointmentList = clientAppointmentList;
        return this;
    }

    public List<Appointment> getMasterAppointmentList() {
        return masterAppointmentList;
    }

    public User setMasterAppointmentList(List<Appointment> masterAppointmentList) {
        this.masterAppointmentList = masterAppointmentList;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                "} " + super.toString();
    }
}
