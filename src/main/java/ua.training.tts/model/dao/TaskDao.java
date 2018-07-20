package ua.training.tts.model.dao;

import ua.training.tts.model.entity.Task;

import java.util.List;

public interface TaskDao extends GeneralDao<Task, Integer> {

    void setStatusById(Integer id, String status);

    List<Task> findAllByEmployeeId(Integer id);

    List<Task> findAllByStatus(String status);

    List<Task> findAllArchived();

    List<Task> findAllByApprovalState(String approvalState);

    void updateTaskByEmployee(Task task);
}
