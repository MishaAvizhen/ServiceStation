package service.impl;

import common.RepairRecordTestData;
import common.RepairRequestTestData;
import common.UserTestData;
import entity.RepairRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import repository.RepairRecordRepository;
import repository.RepairRequestRepository;
import repository.UserRepository;
import service.converters.impl.RepairRequestConverter;
import service.dto.RepairRequestRegistrationDto;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class RepairRequestServiceImplTest {

    @Mock
    private RepairRequestRepository repairRequestRepository;
    @Mock
    private RepairRecordRepository repairRecordRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private RepairRequestConverter repairRequestConverter;

    private RepairRequestServiceImpl repairRequestService;

    private UserTestData userTestData = UserTestData.getInstance();
    private RepairRequestTestData repairRequestTestData = RepairRequestTestData.getInstance();
    private RepairRecordTestData repairRecordTestData = RepairRecordTestData.getInstance();


    @Before
    public void setUp() throws Exception {
        repairRequestConverter = new RepairRequestConverter(userService);
        repairRequestService = new RepairRequestServiceImpl(repairRequestRepository, repairRequestConverter);
        when(userRepository.findByUsername("user")).thenReturn(userTestData.getTestUserByUsername("user"));
        when(repairRequestRepository.findAll()).thenReturn(repairRequestTestData.getAllTestRepairRequest());
        when(repairRecordRepository.findAll()).thenReturn(repairRecordTestData.getAllRepairRecordForTest());
        when(repairRequestRepository.save(any((RepairRequest.class)))).thenAnswer(i -> repairRequestTestData.saveTestRepairRequest((RepairRequest) i.getArguments()[0]));


    }


    private RepairRequest findRepairRequestByUsernameAndCarRemark(String username, String carRemark) {
        for (RepairRequest repairRequest : repairRequestTestData.getAllTestRepairRequest()) {
            if (repairRequest.getUser().getUsername().equals(username) && repairRequest.getCarRemark().equals(carRemark)) {
                return repairRequest;
            }
        }
        return null;
    }


    @Test
    public void getListOfActiveRepairRequestsOfUser() throws Exception {

    }

    @Test
    public void getListOfAllActiveRepairRequests() throws Exception {

    }

    @Test
    public void findAllRepairRequests() throws Exception {
        List<RepairRequest> actualAllRepairRequests = repairRequestService.findAllRepairRequests();
        Assert.assertEquals(repairRequestTestData.getAllTestRepairRequest().size(), actualAllRepairRequests.size());

    }

    @Test
    public void findRepairRequestByUsernameAndCarRemark() throws Exception {
        String username = "userToDelete";
        String carRemark = "testCar";
        RepairRequest actualRepairRequest = repairRequestService.findRepairRequestByUsernameAndCarRemark(username, carRemark);
        RepairRequest expectedRepairRequest = findRepairRequestByUsernameAndCarRemark(username, carRemark);
        Assert.assertEquals("Repair request not found ", expectedRepairRequest, actualRepairRequest);


    }

    @Test
    public void deleteRepairRequestByUsernameAndRepairRequestDescription() throws Exception {

    }

    @Test
    public void registerRepairRequest() throws Exception {
        String registerRepairRequestCarRemark = "testCar2";
        Long repairRequestId = repairRequestTestData.getNextId();
        String username = "user";
        RepairRequestRegistrationDto repairRequestRegistrationDto = new RepairRequestRegistrationDto.Builder()
                .setCarRemark(registerRepairRequestCarRemark)
                .setUsername(username)
                .build();

        repairRequestService.registerRepairRequest(repairRequestRegistrationDto);
        RepairRequest expectedRepairRequest = repairRequestTestData.getRepairRequestById(repairRequestId);

        Assert.assertNotNull(expectedRepairRequest);
        Assert.assertEquals(expectedRepairRequest.getCarRemark(), registerRepairRequestCarRemark);
        Assert.assertEquals(expectedRepairRequest.getUser().getUsername(), username);
    }

    @Test
    public void updateRepairRequest() throws Exception {

    }

}