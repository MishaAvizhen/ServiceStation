package service;

import entity.RepairRequest;

import java.util.Date;

public interface VacationService {

    RepairRequest registerVacationRequest(String masterUsername, Date start, Date end);

}
