package service.impl;

import common.RepairRecordTestData;
import common.RepairRequestTestData;
import common.UserTestData;
import entity.RepairRequest;
import entity.User;
import entity.consts.RepairRequestStatus;
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

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
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

    private List<RepairRequest> allActiveRepairRequests() {
        return repairRequestTestData.getAllTestRepairRequest().stream()
                .filter(repairRequest -> repairRequest.getRepairRequestStatus().equals(RepairRequestStatus.IN_PROGRESS))
                .collect(Collectors.toList());

    }

    private List<RepairRequest> allActiveRepairRequestsOfUser(String username) {
        return repairRequestTestData.getAllTestRepairRequest().stream()
                .filter(repairRequest -> repairRequest.getRepairRequestStatus().equals(RepairRequestStatus.IN_PROGRESS)
                        && repairRequest.getUser().getUsername().equals(username))
                .collect(Collectors.toList());

    }


    @Test
    public void getListOfActiveRepairRequestsOfUser() throws Exception {
        when(repairRequestRepository.findAll()).thenReturn(repairRequestTestData.getAllTestRepairRequest());
        User user = userTestData.getTestUserByUsername("userToUpdate");
        String username = user.getUsername();
        List<RepairRequest> actualActiveRepairRequestList = repairRequestService.getListOfActiveRepairRequestsOfUser(username);
        List<RepairRequest> expectedActiveRepairRequestList = allActiveRepairRequestsOfUser(username);
        Assert.assertEquals(expectedActiveRepairRequestList.size(), actualActiveRepairRequestList.size());

    }

    @Test
    public void getListOfAllActiveRepairRequests() throws Exception {
        when(repairRequestRepository.findAll()).thenReturn(repairRequestTestData.getAllTestRepairRequest());
        List<RepairRequest> actualActiveRepairRequestList = repairRequestService.getListOfAllActiveRepairRequests();
        List<RepairRequest> expectedActiveRepairRequestList = allActiveRepairRequests();
        Assert.assertEquals(expectedActiveRepairRequestList.size(), actualActiveRepairRequestList.size());

    }

    @Test
    public void findAllRepairRequests() throws Exception {
        when(repairRequestRepository.findAll()).thenReturn(repairRequestTestData.getAllTestRepairRequest());
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
        doAnswer(i -> repairRequestTestData.deleteRepairRequestById((Long) i.getArguments()[0])).when(repairRequestRepository).delete(any(Long.class));
        RepairRequest repairRequestToDelete = repairRequestTestData.deleteRepairRequestById(3L);
        String username = repairRequestToDelete.getUser().getUsername();
        String repairRequestDescription = repairRequestToDelete.getRepairRequestDescription();
        repairRequestService.deleteRepairRequestByUsernameAndRepairRequestDescription(username, repairRequestDescription);
        RepairRequest repairRequestToDeleteAfterDelete = repairRequestTestData.getRepairRequestById(3L);
        Assert.assertNull(" repair request was not delete", repairRequestToDeleteAfterDelete);

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
        String usernameToUpdate = "userToUpdate";
        when(userRepository.findByUsername(usernameToUpdate)).thenReturn(userTestData.getTestUserByUsername(usernameToUpdate));
        long repairRequestId = 2L;
        RepairRequest repairRequestToUpdate = repairRequestTestData.getRepairRequestById(repairRequestId);
        String carRemark = repairRequestToUpdate.getCarRemark();
        String newCarRemark = carRemark + " update!";
        String repairRequestDescription = repairRequestToUpdate.getRepairRequestDescription();
        String newRepairRequestDescription = repairRequestDescription + " updated!!!";
        repairRequestService.updateRepairRequest(new RepairRequestRegistrationDto.Builder()
                .setRepairRequestDescription(newRepairRequestDescription)
                .setRepairRequestStatus(RepairRequestStatus.IN_PROGRESS)
                .setDateOfRequest(new Date())
                .setCarRemark(newCarRemark)
                .setUsername(usernameToUpdate)
                .build(), repairRequestToUpdate);
        RepairRequest updatedRepairRequest = repairRequestTestData.getRepairRequestById(repairRequestId);
        Assert.assertNotEquals("car remark wasn't update", carRemark, updatedRepairRequest.getCarRemark());
        Assert.assertNotEquals("repair request description wasn't update", repairRequestDescription, updatedRepairRequest.getRepairRequestDescription());

    }

}