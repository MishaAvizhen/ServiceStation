package service.impl;

import common.RepairRecordTestData;
import common.RepairRequestTestData;
import common.UserTestData;
import entity.RepairRecord;
import entity.RepairRequest;
import entity.enums.RepairRequestStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import repository.RepairRecordRepository;
import repository.RepairRequestRepository;
import repository.UserRepository;
import service.converters.impl.RepairRecordConverter;
import service.dto.RepairRecordRegistrationDto;

import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
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
        repairRecordService = new RepairRecordServiceImpl(repairRecordRepository, repairRequestRepository,
                repairRecordConverter, repairRequestService);
    }

    @Test
    public void findAllRepairRecords() throws Exception {
        when(repairRecordRepository.findAll()).thenReturn(repairRecordTestData.getAllRepairRecordForTest());
        List<RepairRecord> actualRepairRecordList = repairRecordService.findAllRepairRecords();
        Assert.assertEquals(repairRecordTestData.getAllRepairRecordForTest().size(), actualRepairRecordList.size());
    }

    @Test
    public void updateRepairRecord() throws Exception {
        when(repairRequestRepository.findById(any(Long.class))).thenAnswer(i -> Optional.of(repairRequestTestData.getRepairRequestById((Long) i.getArguments()[0])));
        when(repairRecordRepository.findById(any(Long.class))).thenAnswer(i -> Optional.of(repairRecordTestData.getRepairRecordById((Long) i.getArguments()[0])));
        String usernameToUpdate = "user";
        when(repairRecordRepository.getOne(any(Long.class))).thenAnswer(i -> repairRecordTestData.getRepairRecordById((Long) i.getArguments()[0]));
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
                .build(), repairRecordId);
        RepairRecord repairRecordAfterUpdate = repairRecordTestData.getRepairRecordById(repairRecordId);
        RepairRequestStatus status = repairRecordAfterUpdate.getRepairRequest().getRepairRequestStatus();

        Assert.assertNotEquals("work price wasn't update", detailPrice, repairRecordAfterUpdate.getWorkPrice());
        Assert.assertNotEquals("detail price wasn't update", detailPrice, repairRecordAfterUpdate.getDetailPrice());
        Assert.assertNotEquals("repair record description wasn't update", repairRecordDescription, repairRecordAfterUpdate.getRepairRecordDescription());
        Assert.assertEquals("Status was updated", repairRequestStatus, status);
        Assert.assertEquals("User was updated", repairRecordAfterUpdate.getRepairRequest().getUser().getUsername(), usernameToUpdate);
    }
}