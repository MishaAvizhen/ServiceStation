import dao.AppointmentDao;
import dao.UserDao;
import dao.common.impl.BackUpablesManager;
import dao.impl.InMemoryAppointmentDao;
import dao.impl.InMemoryUserDao;
import entity.Appointment;
import entity.BaseEntity;
import entity.User;
import entity.util.Role;
import entity.util.SlotStatus;
import org.apache.commons.lang3.time.DateUtils;
import service.AppointmentService;
import service.AppointmentSlotService;
import service.dto.AppointmentSlotDto;
import service.impl.AppointmentServiceImpl;
import service.impl.AppointmentSlotServiceImpl;
import ui.menu.ConsoleMenu;

import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        BackUpablesManager backUpablesManager = new BackUpablesManager();
        backUpablesManager.readFromFile();
        try {
            ConsoleMenu consoleMenu = new ConsoleMenu();
            consoleMenu.initMenuConsole();
        } finally {
            backUpablesManager.writeToFile();
        }

    }
}
