package service.impl;

import common.AppointmentTestData;
import entity.Appointment;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import repository.AppointmentRepository;
import repository.UserRepository;

import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AppointmentServiceImplTest {
    private AppointmentTestData appointmentTestData = AppointmentTestData.getInstance();
    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void findAllAppointment() throws Exception {
        when(appointmentRepository.findAll()).thenReturn(appointmentTestData.getAllAppointments());
        List<Appointment> actualAppointments = appointmentService.findAllAppointments();
        Assert.assertEquals(appointmentTestData.getAllAppointments().size(), actualAppointments.size());
    }

}