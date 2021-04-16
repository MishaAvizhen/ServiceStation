package service;

import entity.RepairRequest;
import service.dto.VacationRegistrationDto;

import java.util.Date;

public interface VacationService {

    RepairRequest registerVacationRequest(VacationRegistrationDto vacationRegistrationDto);

}
