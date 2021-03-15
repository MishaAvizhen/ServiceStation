package dao;

import dao.common.BackUpable;
import entity.BaseEntity;

import java.io.*;
import java.util.*;

public abstract class AbstractCrudDao<E extends BaseEntity> implements GenericDao<E>, BackUpable {
    private Long nextId = 1L;
    protected Map<Long, E> storeMap = new HashMap<>();
    private static String pathToBackupFolder = "C:\\ProjectsAvizhen\\NetcrackerAvizhenSto\\dao\\src\\main\\java\\docs\\";

    @Override
    public void writeToFile() {
        try (FileOutputStream fileOutputStream = new FileOutputStream(getBackUpFile());
             ObjectOutputStream objectOutputStreamRepairRecordDb = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStreamRepairRecordDb.writeObject(storeMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readFromFile() {

        try (FileInputStream fileInputStream = new FileInputStream(getBackUpFile());
             ObjectInputStream objectInputStreamRepairRecordDb = new ObjectInputStream(fileInputStream);) {
            storeMap = (Map<Long, E>) objectInputStreamRepairRecordDb.readObject();
            Long maxId = Collections.max(storeMap.keySet());
            nextId = maxId + 1;
        } catch (EOFException eof) {
            eof.printStackTrace();
            System.out.println("it's ok");
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public E update(E newValue) {
        storeMap.put(newValue.getId(), newValue);
        return newValue;
    }

    @Override
    public E save(E entity) {
        Long nextId = getNextId();
        entity.setId(nextId);
        storeMap.put(nextId, entity);
        return entity;
    }


    @Override
    public List<E> findAll() {
        List<E> result = new ArrayList<>();
        result.addAll(storeMap.values());
        return result;
    }

    @Override
    public E findById(Long id) {
        return storeMap.get(id);
    }

    @Override
    public void deleteById(Long id) {
        storeMap.remove(id);
    }

    private File getBackUpFile() throws IOException {
        String pathToFile = generateDaoDbFilePath();
        File file = new File(pathToFile);
        file.createNewFile();
        return file;
    }

    private String generateDaoDbFilePath() {
        String className = getClass().getSimpleName();
        return pathToBackupFolder + className + ".bin";
    }

    private Long getNextId() {
        return nextId++;
    }


}
