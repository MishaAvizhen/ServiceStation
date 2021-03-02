package command.impl;

import command.MenuCommand;
import entity.RepairRequest;
import service.RepairRecordService;
import service.RepairRequestService;
import service.dto.RepairRecordRegistrationDto;
import service.impl.RepairRecordServiceImpl;
import service.impl.RepairRequestServiceImpl;

import java.util.List;
import java.util.Scanner;

public class CreateRepairRecordMenuCommand implements MenuCommand {
    private RepairRecordService repairRecordService = RepairRecordServiceImpl.getInstance();
    private RepairRequestService repairRequestService = RepairRequestServiceImpl.getInstance();

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username: ");
        String username = scanner.next();
        System.out.println("Choose repair request:  \n");
        List<RepairRequest> listOfAllRepairRequestsOfUser = repairRequestService.getListOfActiveRepairRequestsOfUser(username);
        for (int i = 0; i < listOfAllRepairRequestsOfUser.size(); i++) {
            RepairRequest request = listOfAllRepairRequestsOfUser.get(i);
            System.out.println(i + ": " + "User: " + request.getUser().getUsername() +
                    ", Car remark: " + request.getCarRemark() +
                    ", Repair request description: " + request.getRepairRequestDescription());
        }
        int repairRequestNumber = scanner.nextInt();
        RepairRequest selectedRepairRequest = listOfAllRepairRequestsOfUser.get(repairRequestNumber);
        Long id = selectedRepairRequest.getId();
        System.out.println("Enter repair record description: ");
        String repairRecordDescription = scanner.next();
        System.out.println("Enter other notes: ");
        String otherNotes = scanner.next();
        System.out.println("Enter detail price: ");
        Long detailPrice = scanner.nextLong();
        System.out.println("Enter work price: ");
        Long workPrice = scanner.nextLong();


        RepairRecordRegistrationDto repairRecordRegistrationDto = new RepairRecordRegistrationDto.Builder()
                .setRepairRecordDescription(repairRecordDescription)
                .setRepairRequest(id)
                .setDetailPrice(detailPrice)
                .setWorkPrice(workPrice)
                .setOtherNotes(otherNotes)
                .build();
        repairRecordService.createRepairRecord(repairRecordRegistrationDto);
        System.out.println("Repair record for " + username + " was created");

    }
}
