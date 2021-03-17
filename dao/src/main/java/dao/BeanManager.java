package dao;

import dao.common.DaoPropertyReader;
import dao.impl.InMemoryAppointmentDao;
import dao.impl.InMemoryRepairRecordDao;
import dao.impl.InMemoryRepairRequestDao;
import dao.impl.InMemoryUserDao;
import dao.impl.db.DbAppointmentDao;
import dao.impl.db.DbRepairRecordDao;
import dao.impl.db.DbRepairRequestDao;
import dao.impl.db.DbUserDao;

public class BeanManager {
    private static BeanManager beanManager;
    private DaoPropertyReader daoPropertyReader = new DaoPropertyReader();

    private BeanManager() {
    }

    public static BeanManager getInstance() {
        if (beanManager == null) {
            beanManager = new BeanManager();
        }
        return beanManager;
    }

    public UserDao getUserDao() {
        if (daoPropertyReader.useFileMemory()) {
            return InMemoryUserDao.getInstance();
        } else {
            return DbUserDao.getInstance();
        }
    }

    public RepairRecordDao getRepairRecord() {
        if (daoPropertyReader.useFileMemory()) {
            return InMemoryRepairRecordDao.getInstance();
        } else {

            return DbRepairRecordDao.getInstance();
        }
    }

    public RepairRequestDao getRepairRequest() {
        if (daoPropertyReader.useFileMemory()) {
            return InMemoryRepairRequestDao.getInstance();
        } else {
            return DbRepairRequestDao.getInstance();
        }
    }

    public AppointmentDao getAppointment() {
        if (daoPropertyReader.useFileMemory()) {
            return InMemoryAppointmentDao.getInstance();
        } else {
            return DbAppointmentDao.getInstance();
        }
    }

}
