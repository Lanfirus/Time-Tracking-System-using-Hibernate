package ua.training.tts.model.dao;

import ua.training.tts.model.entity.full.FullTask;

import java.sql.SQLException;
import java.util.List;

public interface FullTaskDao extends GeneralDao<FullTask, Integer> {

    List<FullTask> findAllProjectsByEmployeeId(Integer id);

    void archiveProjectAndTasks(Integer id) throws SQLException;
}
