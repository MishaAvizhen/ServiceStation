import dao.UserDao;
import dao.impl.db.DbRepairRecordDao;
import dao.impl.db.DbRepairRequestDao;
import dao.impl.db.DbUserDao;
import db.JdbcInit;
import db.JdbcTemplate;
import entity.RepairRecord;
import entity.RepairRequest;
import entity.User;
import entity.constants.RepairRequestStatus;

import java.util.List;

public class DaoMain {

    public static void main(String[] args) {

        DbRepairRecordDao dbRepairRecordDao = DbRepairRecordDao.getInstance();
        RepairRecord byId1 = dbRepairRecordDao.findById(1L);
        System.out.println("Find by id:" + byId1);
        List<RepairRecord> all = dbRepairRecordDao.findAll();
        System.out.println("All: " + all);
        byId1.setWorkPrice(500L);
        byId1.setDetailPrice(500L);
        RepairRecord update = dbRepairRecordDao.update(byId1);
        System.out.println("Update: " + update);
        RepairRecord save = dbRepairRecordDao.save(byId1);
        System.out.println("save: " + save);

    }
}
