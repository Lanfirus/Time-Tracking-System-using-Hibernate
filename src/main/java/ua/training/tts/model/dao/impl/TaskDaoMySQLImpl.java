package ua.training.tts.model.dao.impl;

import org.hibernate.Session;
import ua.training.tts.constant.ExceptionMessages;
import ua.training.tts.constant.model.dao.TableParameters;
import ua.training.tts.controller.util.HibernateUtil;
import ua.training.tts.model.dao.TaskDao;
import ua.training.tts.model.dao.connectionpool.ConnectionPool;
import ua.training.tts.model.entity.Employee;
import ua.training.tts.model.entity.Project;
import ua.training.tts.model.entity.Task;
import ua.training.tts.model.exception.DataChangeDetectedException;
import ua.training.tts.model.util.RequestBuilder;
import ua.training.tts.model.util.builder.TaskBuilder;
import ua.training.tts.util.LogMessageHolder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskDaoMySQLImpl implements TaskDao {

    private RequestBuilder builder;
    private String savedStatement;

    public TaskDaoMySQLImpl(RequestBuilder builder){
        this.builder = builder;
    }


    @Override
    public Task findById(Integer id) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Task task = session.load(Task.class, id);
        session.getTransaction().commit();
        session.close();
        return task;
    }

    /**
     * Builds Task entity from data gotten from database
     *
     * @param set       Result set with data from database
     * @return          Task entity filled with data from database
     * @throws SQLException
     */
    private Task extractDataFromResultSet(ResultSet set) throws SQLException{
        TaskBuilder builder = new TaskBuilder();
        Task task = builder.setId(set.getInt(TableParameters.TASK_ID))
                           .setProjectId(set.getInt(TableParameters.TASK_PROJECT_ID))
                           .setEmployeeId(set.getInt(TableParameters.TASK_EMPLOYEE_ID))
                           .setName(set.getString(TableParameters.TASK_NAME))
                           .setStatus(set.getString(TableParameters.TASK_STATUS))
                           .setDeadline(set.getString(TableParameters.TASK_DEADLINE))
                           .setSpentTime(set.getInt(TableParameters.TASK_SPENT_TIME))
                           .setApprovalState(set.getString(TableParameters.TASK_APPROVAL_STATE))
                           .buildTask();
        return task;
    }

    @Override
    public List<Task> findAll() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Task> criteria = builder.createQuery(Task.class);
        Root<Task> taskRoot = criteria.from(Task.class);
        criteria.select(taskRoot);
        List<Task> taskList = session.createQuery(criteria).getResultList();
        session.getTransaction().commit();
        session.close();
        return taskList;
    }

    /**
     * Gathers information about all tasks put into archive
     *
     * @return      List of archived tasks
     */
    @Override
    public List<Task> findAllArchived() {
        builder.clear();
        List<Task> resultList = new ArrayList<>();
        String request = builder.selectAllFromTable(TableParameters.TASK_ARCHIVE_TABLE_NAME)
                                .build();
        try (Connection connection = ConnectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(request)){
            savedStatement = statement.toString();
            ResultSet set = statement.executeQuery();
            while (set.next()){
                Task result = extractDataFromResultSet(set);
                resultList.add(result);
            }
        } catch (SQLException e) {
            log.error(LogMessageHolder.recordSearchingInTableProblem(TableParameters.TASK_TABLE_NAME,
                                                                                            savedStatement), e);
            throw new RuntimeException(ExceptionMessages.SQL_GENERAL_PROBLEM);
        }
        return resultList;
    }

    /**
     * Gathers information about all tasks assigned to specified employee
     *
     * @param id    Employee's id
     * @return      List of tasks assigned to specified employee
     */
    @Override
    public List<Task> findAllByEmployeeId(Integer id) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Task> criteria = builder.createQuery(Task.class);
        Root<Task> taskRoot = criteria.from(Task.class);
        criteria.select(taskRoot);
        criteria.where(taskRoot.get("employeeId").in(id));
        List<Task> taskList = session.createQuery(criteria).getResultList();
        session.getTransaction().commit();
        session.close();
        return taskList;
    }

    /**
     * Gathers information about all tasks that satisfy provided status
     *
     * @param status        Task status provided by user
     * @return              List of tasks that satisfy provided task status
     */
    @Override
    public List<Task> findAllByStatus(String status) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Task> criteria = builder.createQuery(Task.class);
        Root<Task> taskRoot = criteria.from(Task.class);
        criteria.select(taskRoot);
        criteria.where(taskRoot.get("status").in(Task.Status.valueOf(status.toUpperCase())));
        List<Task> taskList = session.createQuery(criteria).getResultList();
        session.getTransaction().commit();
        session.close();
        return taskList;
    }

    /**
     * Gathers information about all tasks that satisfy provided approval state
     *
     * @param approvalState        Task approval state provided by user
     * @return                     List of tasks that satisfy provided task approval state
     */
    @Override
    public List<Task> findAllByApprovalState(String approvalState) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Task> criteria = builder.createQuery(Task.class);
        Root<Task> taskRoot = criteria.from(Task.class);
        criteria.select(taskRoot);
        criteria.where(taskRoot.get("approvalState").in(Task.ApprovalState.valueOf(approvalState.toUpperCase())));
        List<Task> taskList = session.createQuery(criteria).getResultList();
        session.getTransaction().commit();
        session.close();
        return taskList;
    }

    /**
     * Updates task using data provided by Employees
     *
     * @param task      Task created with user provided data
     * @throws DataChangeDetectedException      Thrown if system detects that user has used outdated data during
     *                                          task update
     */
    @Override
    public void updateTaskByEmployee(Task task) throws DataChangeDetectedException {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Task taskFromDB = session.load(Task.class, task.getId());
        if (taskFromDB.getApprovalState() == Task.ApprovalState.APPROVED
                && (taskFromDB.getStatus() == Task.Status.CANCELLED
                || taskFromDB.getStatus() == Task.Status.FINISHED)) {
            throw new DataChangeDetectedException();
        } else {
            taskFromDB.setStatus(task.getStatus());
            taskFromDB.setSpentTime(task.getSpentTime());
            taskFromDB.setApprovalState(task.getApprovalState());
            //session.merge(taskFromDB);
        }
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Integer id) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.createQuery("delete from Task where id= :id").setParameter("id", id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void setStatusById(Integer id, String status) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.createQuery("update Task set status = :status where id = :id")
                .setParameter("status", Task.Status.valueOf(status.toUpperCase()))
                .setParameter("id", id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}