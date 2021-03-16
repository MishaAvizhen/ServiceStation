import dao.RepairRecordDao;
import dao.impl.db.DbRepairRecordDao;
import entity.RepairRecord;

import java.util.List;

public class DaoMain {

    public static void main(String[] args) {
        RepairRecordDao repairRecordDao = DbRepairRecordDao.getInstance();
        List<RepairRecord> all = repairRecordDao.findAll();
        System.out.println(all);

    }
}
