package service.dto;

import entity.User;

import java.time.LocalDateTime;

public class AppointmentSlotDto {
    private User master;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public AppointmentSlotDto(User master, LocalDateTime startDate, LocalDateTime endDate) {
        this.master = master;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public AppointmentSlotDto() {
    }

    public User getMaster() {
        return master;
    }

    public void setMaster(User master) {
        this.master = master;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "AppointmentSlotDto{" +
                "master=" + master +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
