package service.converters.impl;

import dao.AppointmentDao;
import dao.RepairRecordDao;
import dao.RepairRequestDao;
import dao.UserDao;
import dao.impl.InMemoryAppointmentDao;
import dao.impl.InMemoryRepairRecordDao;
import dao.impl.InMemoryRepairRequestDao;
import dao.impl.InMemoryUserDao;
import entity.Appointment;
import entity.RepairRecord;
import entity.RepairRequest;
import entity.User;
import entity.util.RepairRequestStatus;
import entity.util.Role;
import entity.util.SlotStatus;
import service.UserService;
import service.converters.ConverterEntityDtoService;
import service.dto.AppointmentSlotDto;
import service.dto.RepairRecordRegistrationDto;
import service.dto.RepairRequestRegistrationDto;
import service.dto.UserRegistrationDto;
import service.impl.UserServiceImpl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class ConverterEntityDtoImpl implements ConverterEntityDtoService {
    private UserDao userDao = InMemoryUserDao.getInstance();
    private UserService userService = UserServiceImpl.getInstance();
    private RepairRequestDao repairRequestDao = InMemoryRepairRequestDao.getInstance();
    private RepairRecordDao repairRecordDao = InMemoryRepairRecordDao.getInstance();
    private AppointmentDao appointmentDao = InMemoryAppointmentDao.getInstance();

    private static ConverterEntityDtoImpl converterEntityDto;

    private ConverterEntityDtoImpl() {
    }

    public static ConverterEntityDtoImpl getInstance() {
        if (converterEntityDto == null) {
            converterEntityDto = new ConverterEntityDtoImpl();
        }
        return converterEntityDto;
    }

    @Override
    public void createUser(UserRegistrationDto userRegistrationDto) {
        User user = new User();
        user.setUsername(userRegistrationDto.getUsername());
        user.setPhoneNumber(userRegistrationDto.getPhoneNumber());
        user.setEmail(userRegistrationDto.getEmail());
        user.setRole(Role.USER_ROLE);
        user.setPassword(userRegistrationDto.getPassword());
        userDao.save(user);

    }

    @Override
    public void createRepairRequest(RepairRequestRegistrationDto repairRequestRegistrationDto) {
        User userByUsername = userService.findUserByUsername(repairRequestRegistrationDto.getUsername());
        RepairRequest repairRequest = new RepairRequest();
        repairRequest.setDateOfRequest(repairRequestRegistrationDto.getDateOfRequest());
        repairRequest.setCarRemark(repairRequestRegistrationDto.getCarRemark());
        repairRequest.setRepairRequestStatus(RepairRequestStatus.IN_PROGRESS_STATUS);
        repairRequest.setRepairRequestDescription(repairRequestRegistrationDto.getRepairRequestDescription());
        repairRequest.setUser(userByUsername);
        repairRequestDao.save(repairRequest);
    }

    @Override
    public void createRepairRecord(RepairRecordRegistrationDto repairRecordRegistrationDto) {
        RepairRequest repairRequestDaoById = repairRequestDao.findById(repairRecordRegistrationDto.getRepairRequestId());
        RepairRecord repairRecord = new RepairRecord();
        repairRecord.setRepairRequest(repairRequestDaoById);
        repairRecord.setOtherNotes(repairRecordRegistrationDto.getOtherNotes());
        repairRecord.setRepairRecordDescription(repairRecordRegistrationDto.getRepairRecordDescription());
        repairRecord.setDetailPrice(repairRecordRegistrationDto.getDetailPrice());
        repairRecord.setWorkPrice(repairRecordRegistrationDto.getWorkPrice());
        repairRequestDaoById.setRepairRequestStatus(RepairRequestStatus.PROCESSED_STATUS);
        repairRecordDao.save(repairRecord);


    }

    @Override
    public Appointment createAppointment(AppointmentSlotDto appointmentSlotDto, Long userId) {
        LocalDateTime startDateInLocalDate = appointmentSlotDto.getStartDate();
        LocalDateTime endDateInLocalDate = appointmentSlotDto.getEndDate();
        ZonedDateTime zdt = startDateInLocalDate.atZone(ZoneId.systemDefault());
        ZonedDateTime zdt1 = endDateInLocalDate.atZone(ZoneId.systemDefault());
        Date startDateInDate = Date.from(zdt.toInstant());
        Date endDateInDate = Date.from(zdt1.toInstant());
        Appointment appointment = new Appointment();
        appointment.setMaster(appointmentSlotDto.getMaster());
        appointment.setStartDate(startDateInDate);
        appointment.setEndDate(endDateInDate);
        appointment.setSlotStatus(SlotStatus.BUSY_STATUS);
        appointment.setClient(userDao.findById(userId));
        appointment.setNotes(" notes...");
        return appointmentDao.save(appointment);

    }
}
