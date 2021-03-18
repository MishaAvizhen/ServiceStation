package dao.common.impl;

import dao.common.BackUpable;
import dao.impl.InMemoryAppointmentDao;
import dao.impl.InMemoryRepairRecordDao;
import dao.impl.InMemoryRepairRequestDao;
import dao.impl.InMemoryUserDao;

import java.util.ArrayList;
import java.util.List;

public class BackUpablesManager {
    private final List<BackUpable> backUpableList = new ArrayList<>();

    public BackUpablesManager() {
        InMemoryUserDao userDao = InMemoryUserDao.getInstance();
        InMemoryRepairRequestDao repairRequestDao = InMemoryRepairRequestDao.getInstance();
        InMemoryRepairRecordDao repairRecordDao = InMemoryRepairRecordDao.getInstance();
        InMemoryAppointmentDao AppointmentDao = dao.impl.InMemoryAppointmentDao.getInstance();
        this.backUpableList.add(userDao);
        this.backUpableList.add(repairRequestDao);
        this.backUpableList.add(repairRecordDao);
        this.backUpableList.add(AppointmentDao);
    }

    public void writeToFile() {
        for (BackUpable backUpable : backUpableList) {
            backUpable.writeToFile();
        }
    }

    public void readFromFile() {
        for (BackUpable backUpable : backUpableList) {
            try {
                backUpable.readFromFile();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(backUpable.getClass().getSimpleName() + " не смог загрузить данные");
            }
        }
    }


}