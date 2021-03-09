import dao.UserDao;
import dao.impl.db.DbRepairRequestDao;
import dao.impl.db.DbUserDao;
import db.JdbcInit;
import db.JdbcTemplate;
import entity.RepairRequest;
import entity.User;
import entity.constants.RepairRequestStatus;

import java.util.List;

public class DaoMain {

    public static void main(String[] args) {

        DbRepairRequestDao dbRepairRequestDao = DbRepairRequestDao.getInstance();
        DbUserDao dbUserDao = DbUserDao.getInstance();
        User byId11 = dbUserDao.findById(11L);
        RepairRequest byId = dbRepairRequestDao.findById(2L);
        System.out.println("Find by id:" + byId);
        byId.setRepairRequestStatus(RepairRequestStatus.CANCELLED_STATUS);
        byId.setCarRemark("tesla");
        byId.setUser(byId11);
        dbRepairRequestDao.update(byId);
        dbRepairRequestDao.deleteById(7L);


    }
}
