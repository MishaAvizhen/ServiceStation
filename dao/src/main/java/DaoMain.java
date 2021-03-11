import dao.AppointmentDao;
import dao.RepairRecordDao;
import dao.UserDao;
import dao.impl.db.DbAppointmentDao;
import dao.impl.db.DbRepairRecordDao;
import dao.impl.db.DbRepairRequestDao;
import dao.impl.db.DbUserDao;
import db.JdbcInit;
import db.JdbcTemplate;
import entity.Appointment;
import entity.RepairRecord;
import entity.RepairRequest;
import entity.User;
import entity.constants.RepairRequestStatus;
import entity.constants.SlotStatus;

import java.util.List;

public class DaoMain {

    public static void main(String[] args) {
        RepairRecordDao repairRecordDao = DbRepairRecordDao.getInstance();
        List<RepairRecord> all = repairRecordDao.findAll();
        System.out.println(all);

    }
}
