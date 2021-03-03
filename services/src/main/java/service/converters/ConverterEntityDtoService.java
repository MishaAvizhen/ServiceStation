package service.converters;

import entity.Appointment;
import service.dto.AppointmentSlotDto;
import service.dto.RepairRecordRegistrationDto;
import service.dto.RepairRequestRegistrationDto;
import service.dto.UserRegistrationDto;

public interface ConverterEntityDtoService {

    void createUser(UserRegistrationDto userRegistrationDto);

    void createRepairRequest(RepairRequestRegistrationDto repairRequestRegistrationDto);

    void createRepairRecord(RepairRecordRegistrationDto repairRecordRegistrationDto);

    Appointment createAppointment(AppointmentSlotDto appointmentSlotDto, Long userId);


}
