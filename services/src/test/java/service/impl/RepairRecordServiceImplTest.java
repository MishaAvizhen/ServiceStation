package service.impl;

import common.RepairRecordTestData;
import common.RepairRequestTestData;
import common.UserTestData;
import entity.RepairRecord;
import entity.RepairRequest;
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
import service.converters.impl.RepairRecordConverter;
import service.dto.RepairRecordRegistrationDto;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RepairRecordServiceImplTest {
    private UserTestData userTestData = UserTestData.getInstance();
    private RepairRecordTestData repairRecordTestData = RepairRecordTestData.getInstance();
    private RepairRequestTestData repairRequestTestData = RepairRequestTestData.getInstance();
    @Mock
    private RepairRecordRepository repairRecordRepository;
    @Mock
    private RepairRequestRepository repairRequestRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private RepairRecordServiceImpl repairRecordService;
    @InjectMocks
    private RepairRequestServiceImpl repairRequestService;

    private RepairRecordConverter repairRecordConverter;

    @Before
    public void setUp() throws Exception {
        repairRecordConverter = new RepairRecordConverter(repairRequestRepository);
        repairRecordService = new RepairRecordServiceImpl(repairRecordRepository, repairRequestRepository, repairRecordConverter);
    }


    private RepairRecord findRepairRecordByUsernameAndRepairRecordDescription(String username, String repairRecordDescription) {
        for (RepairRecord repairRecord : repairRecordTestData.getAllRepairRecordForTest()) {
            if (repairRecord.getRepairRequest().getUser().getUsername().equals(username) &&
                    repairRecord.getRepairRecordDescription().equals(repairRecordDescription)) {
                return repairRecord;
            }
        }
        return null;
    }

    @Test
    public void findAllRepairRecords() throws Exception {
        when(repairRecordRepository.findAll()).thenReturn(repairRecordTestData.getAllRepairRecordForTest());
        List<RepairRecord> actualRepairRecordList = repairRecordService.findAllRepairRecords();
        Assert.assertEquals(repairRecordTestData.getAllRepairRecordForTest().size(), actualRepairRecordList.size());

    }

    @Test
    public void findRepairRecordByUsernameAndRepairRecordDescription() throws Exception {
        String username = "userToDelete";
        String repairRecordDescription = "test repair record description";
        when(repairRecordRepository.findAll()).thenReturn(repairRecordTestData.getAllRepairRecordForTest());
        RepairRecord actualRepairRecord = repairRecordService.findRepairRecordByUsernameAndRepairRecordDescription(username, repairRecordDescription);
        RepairRecord expectedRepairRecord = findRepairRecordByUsernameAndRepairRecordDescription(username, repairRecordDescription);
        Assert.assertEquals("Repair record not found", expectedRepairRecord, actualRepairRecord);
        Assert.assertNotNull("user or repair record description  not found", actualRepairRecord);

    }

    @Test
    public void deleteRepairRecordByUsernameAndRepairRecordDescription() throws Exception {

        doAnswer(i -> repairRecordTestData.deleteRepairRecordById((Long) i.getArguments()[0])).when(repairRecordRepository).delete(any(Long.class));
        long repairRecordId = 1L;
        RepairRecord repairRecordToDelete = repairRecordTestData.deleteRepairRecordById(repairRecordId);
        String username = repairRecordToDelete.getRepairRequest().getUser().getUsername();
        String repairRecordDescription = repairRecordToDelete.getRepairRecordDescription();
        repairRecordService.deleteRepairRecordByUsernameAndRepairRecordDescription(username, repairRecordDescription);
        RepairRecord repairRecordToDeleteAfterDelete = repairRecordTestData.getRepairRecordById(repairRecordId);
        Assert.assertNull(" repair record was not delete", repairRecordToDeleteAfterDelete);

    }

    @Test
    public void registerRepairRecord() throws Exception {
        when(repairRecordRepository.save(any((RepairRecord.class)))).thenAnswer(i -> repairRecordTestData.saveTestRepairRecord((RepairRecord) i.getArguments()[0]));
        when(repairRequestRepository.save(any((RepairRequest.class)))).thenAnswer(i -> repairRequestTestData.saveTestRepairRequest((RepairRequest) i.getArguments()[0]));
        when(repairRequestRepository.findOne(any(Long.class))).thenAnswer(i -> repairRequestTestData.getRepairRequestById((Long) i.getArguments()[0]));

        String repairRecordDescription = "test record description new";
        Long workPrice = 323L;
        Long detailPrice = 101L;
        long nextRepairRecordId = repairRecordTestData.getNextId();
        long repairRequestId = 1L;
        String username = "user";
        String otherNotes = "test other notes";
        RepairRecordRegistrationDto repairRecordRegistrationDto = new RepairRecordRegistrationDto.Builder()
                .setRepairRecordDescription(repairRecordDescription)
                .setWorkPrice(workPrice)
                .setDetailPrice(detailPrice)
                .setRepairRequest(repairRequestId)
                .setOtherNotes(otherNotes)
                .build();

        repairRecordService.registerRepairRecord(repairRecordRegistrationDto);
        RepairRecord actualRepairRecord = repairRecordTestData.getRepairRecordById(nextRepairRecordId);

        Assert.assertNotNull(actualRepairRecord);
        Assert.assertEquals(actualRepairRecord.getRepairRequest().getRepairRequestStatus(), RepairRequestStatus.PROCESSED);
        Assert.assertEquals(actualRepairRecord.getRepairRecordDescription(), repairRecordDescription);
        Assert.assertEquals(actualRepairRecord.getDetailPrice(), detailPrice);
        Assert.assertEquals(actualRepairRecord.getWorkPrice(), workPrice);
        Assert.assertEquals(actualRepairRecord.getRepairRequest().getUser().getUsername(), username);
    }

    @Test
    public void updateRepairRecord() throws Exception {

        String usernameToUpdate = "user";
        when(repairRequestRepository.findOne(any(Long.class))).thenAnswer(i -> repairRequestTestData.getRepairRequestById((Long) i.getArguments()[0]));
        when(userRepository.findByUsername(usernameToUpdate)).thenReturn(userTestData.getTestUserByUsername(usernameToUpdate));
        long repairRecordId = 1L;
        RepairRecord repairRecordByIdToUpdate = repairRecordTestData.getRepairRecordById(repairRecordId);
        Long repairRequestId = repairRecordByIdToUpdate.getRepairRequest().getId();
        RepairRequestStatus repairRequestStatus = repairRecordByIdToUpdate.getRepairRequest().getRepairRequestStatus();
        String repairRecordDescription = repairRecordByIdToUpdate.getRepairRecordDescription();
        String newRepairRecordDescription = repairRecordDescription + " update desc!";
        Long workPrice = repairRecordByIdToUpdate.getWorkPrice();
        Long newWorkPrice = workPrice + 1000L;
        Long detailPrice = repairRecordByIdToUpdate.getDetailPrice();
        Long newDetailPrice = detailPrice + 500L;

        repairRecordService.updateRepairRecord(new RepairRecordRegistrationDto.Builder()
                .setDetailPrice(newDetailPrice)
                .setRepairRecordDescription(newRepairRecordDescription)
                .setWorkPrice(newWorkPrice)
                .setRepairRequest(repairRequestId)
                .build(), repairRecordByIdToUpdate);
        RepairRecord repairRecordAfterUpdate = repairRecordTestData.getRepairRecordById(repairRecordId);

        RepairRequestStatus status = repairRecordAfterUpdate.getRepairRequest().getRepairRequestStatus();

        Assert.assertNotEquals("work price wasn't update", detailPrice, repairRecordAfterUpdate.getWorkPrice());
        Assert.assertNotEquals("detail price wasn't update", detailPrice, repairRecordAfterUpdate.getDetailPrice());
        Assert.assertNotEquals("repair record description wasn't update", repairRecordDescription, repairRecordAfterUpdate.getRepairRecordDescription());
        Assert.assertEquals("Status was updated", repairRequestStatus, status);
        Assert.assertEquals("User was updated", repairRecordAfterUpdate.getRepairRequest().getUser().getUsername(), usernameToUpdate);


    }

}