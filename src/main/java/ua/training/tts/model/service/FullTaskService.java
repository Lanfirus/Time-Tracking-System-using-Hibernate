package ua.training.tts.model.service;

import ua.training.tts.constant.ExceptionMessages;
import ua.training.tts.model.dao.FullTaskDao;
import ua.training.tts.model.dao.factory.DaoFactory;
import ua.training.tts.model.entity.full.FullTask;

import java.sql.SQLException;
import java.util.List;

public class FullTaskService {

    private FullTaskDao dao = DaoFactory.getInstance().createFullTaskDao();

    public List<FullTask> findAllProjectsByEmployeeId(Integer id){
        return dao.findAllProjectsByEmployeeId(id);
    }

    public void archiveProject(Integer id){
        try {
            dao.archiveProjectAndTasks(id);
        } catch (SQLException e) {
            throw new RuntimeException(ExceptionMessages.SQL_GENERAL_PROBLEM);
        }
    }
}