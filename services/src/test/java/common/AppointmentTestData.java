package common;

import entity.Appointment;
import entity.User;
import entity.enums.Role;
import entity.enums.SlotStatus;

import java.util.*;

public class AppointmentTestData {
    private UserTestData userTestData = UserTestData.getInstance();
    private Map<Long, Appointment> idToTestAppointmentMap = new HashMap<>();
    private static AppointmentTestData INSTANCE = null;

    private AppointmentTestData() {
        initAppointmentTestData();

    }

    public static AppointmentTestData getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AppointmentTestData();
        }
        return INSTANCE;
    }

    public Long getUserIdByUsername() {
        User testUserByUsername = userTestData.getTestUserByUsername("user");
        return testUserByUsername.getId();
    }

    private void initAppointmentTestData() {
        saveTestAppointment(buildAppointmentForTest(getUserIdByUsername()));

    }

    public List<Appointment> getAllAppointments() {
        return new ArrayList<>(idToTestAppointmentMap.values());
    }

    private Appointment saveTestAppointment(Appointment testAppointment) {
        if (testAppointment.getId() == null) {
            testAppointment.setId(getNextId());
        }
        idToTestAppointmentMap.put(testAppointment.getId(), testAppointment);
        return testAppointment;
    }

    public long getNextId() {
        return idToTestAppointmentMap.size() + 1L;
    }

    private Date getStartDate() {
        return new Date();

    }

    private Date getEndDate(Date startDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        cal.add(Calendar.HOUR, +1);
        return cal.getTime();
    }

    private Appointment buildAppointmentForTest(Long userId) {
        Appointment appointment = new Appointment();
        User user = new User();
        user.setRole(Role.MASTER);
        appointment.setId(1L);
        appointment.setClient(userTestData.getTestUserByUsername("user"));
        appointment.setMaster(user);
        appointment.setSlotStatus(SlotStatus.BUSY);
        appointment.setStartDate(getStartDate());
        appointment.setEndDate(getEndDate(new Date()));
        return appointment;
    }
}
