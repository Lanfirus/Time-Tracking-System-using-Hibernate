package ua.training.tts.model.dao.impl;

import ua.training.tts.constant.ExceptionMessages;
import ua.training.tts.constant.model.dao.TableParameters;
import ua.training.tts.model.dao.TaskDao;
import ua.training.tts.model.dao.connectionpool.ConnectionPool;
import ua.training.tts.model.entity.Task;
import ua.training.tts.model.exception.DataChangeDetectedException;
import ua.training.tts.model.util.RequestBuilder;
import ua.training.tts.model.util.builder.TaskBuilder;
import ua.training.tts.util.LogMessageHolder;

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
    public void create(Task task) {
        builder.clear();
        String request = builder.insertIntoTable(TableParameters.TASK_TABLE_NAME)
                                .insertValueNames(getFieldNames())
                                .build();
        try (Connection connection = ConnectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(request)) {
            statement.setInt(1, task.getProjectId());
            statement.setInt(2, task.getEmployeeId());
            statement.setString(3, task.getName());
            statement.setString(4, task.getStatus().name().toLowerCase());
            statement.setDate(5, Date.valueOf(task.getDeadline()));
            statement.setInt(6, task.getSpentTime());
            statement.setString(7, task.getApprovalState().name().toLowerCase());
            savedStatement = statement.toString();
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(LogMessageHolder.recordInsertionToTableProblem(TableParameters.TASK_TABLE_NAME,
                                                                                        savedStatement), e);
            if (e.getMessage().contains(ExceptionMessages.CANNOT_ADD_UPDATE_CHILD_ROW)){
                throw new DataChangeDetectedException();
            }
            else {
                throw new RuntimeException(ExceptionMessages.SQL_GENERAL_PROBLEM);
            }
        }
    }

    @Override
    public Task findById(Integer id) {
        builder.clear();
        String request = builder.selectAllFromTable(TableParameters.TASK_TABLE_NAME)
                                .where(TableParameters.TASK_ID)
                                .build();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(request)) {
            statement.setInt(1,id);
            savedStatement = statement.toString();
            ResultSet set = statement.executeQuery();
            set.next();
            return extractDataFromResultSet(set);
        }
        catch (SQLException e) {
            log.error(LogMessageHolder.recordSearchingInTableProblem(TableParameters.TASK_TABLE_NAME,
                    savedStatement), e);
            throw new RuntimeException(ExceptionMessages.SQL_GENERAL_PROBLEM);
        }
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
        builder.clear();
        List<Task> resultList = new ArrayList<>();
        String request = builder.selectAllFromTable(TableParameters.TASK_TABLE_NAME)
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
        builder.clear();
        List<Task> resultList = new ArrayList<>();
        String request = builder.selectAllFromTable(TableParameters.TASK_TABLE_NAME)
                                .where(TableParameters.TASK_EMPLOYEE_ID)
                                .build();
        try (Connection connection = ConnectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(request)){
            statement.setInt(1, id);
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
     * Gathers information about all tasks that satisfy provided status
     *
     * @param status        Task status provided by user
     * @return              List of tasks that satisfy provided task status
     */
    @Override
    public List<Task> findAllByStatus(String status) {
        builder.clear();
        List<Task> resultList = new ArrayList<>();
        String request = builder.selectAllFromTable(TableParameters.TASK_TABLE_NAME)
                                .where(TableParameters.TASK_STATUS)
                                .build();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(request)){
            statement.setString(1, status);
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
     * Gathers information about all tasks that satisfy provided approval state
     *
     * @param approvalState        Task approval state provided by user
     * @return                     List of tasks that satisfy provided task approval state
     */
    @Override
    public List<Task> findAllByApprovalState(String approvalState) {
        builder.clear();
        List<Task> resultList = new ArrayList<>();
        String request = builder.selectAllFromTable(TableParameters.TASK_TABLE_NAME)
                                .where(TableParameters.TASK_APPROVAL_STATE)
                                .build();
        try (Connection connection = ConnectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(request)){
            statement.setString(1, approvalState);
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

    @Override
    public void update(Task task) {
        builder.clear();
        String request = builder.update(TableParameters.TASK_TABLE_NAME, getFieldNames())
                                .where(TableParameters.TASK_ID)
                                .build();
        try (Connection connection = ConnectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(request)){
            statement.setInt(1, task.getProjectId());
            statement.setInt(2, task.getEmployeeId());
            statement.setString(3, task.getName());
            statement.setString(4, task.getStatus().name().toLowerCase());
            statement.setDate(5, Date.valueOf(task.getDeadline()));
            statement.setInt(6, task.getSpentTime());
            statement.setString(7, task.getApprovalState().name().toLowerCase());
            statement.setInt(8, task.getId());
            savedStatement = statement.toString();
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(LogMessageHolder.recordUpdatingInTableProblem(TableParameters.TASK_TABLE_NAME,
                                                                                            savedStatement), e);
            throw new RuntimeException(ExceptionMessages.SQL_GENERAL_PROBLEM);
        }
    }

    /**
     * Updates task using data provided by Employees
     *
     * @param task      Task created with user provided data
     * @throws DataChangeDetectedException      Thrown if system detects that user has used outdated data during
     *                                          task update
     */
    @Override
    public void updateTaskByEmployee(Task task) throws DataChangeDetectedException{
        try (Connection connection = ConnectionPool.getConnection()) {
            builder.clear();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            Task taskFromDB = getTaskData(connection, task.getId());
            if (taskFromDB.getApprovalState() == Task.ApprovalState.APPROVED
                        && (taskFromDB.getStatus() == Task.Status.CANCELLED
                        || taskFromDB.getStatus() == Task.Status.FINISHED)) {
                throw new DataChangeDetectedException();
            }
            else {
                updateTaskData(connection, task);
                connection.commit();
                connection.setAutoCommit(true);
            }
        }
        catch (SQLException e) {
            log.error(LogMessageHolder.recordUpdatingInTableProblem(TableParameters.TASK_TABLE_NAME,
                    savedStatement), e);
            if (e.getMessage().contains(ExceptionMessages.EMPTY_RESULT_SET)){
                throw new DataChangeDetectedException();
            }
            else {
                throw new RuntimeException(ExceptionMessages.SQL_GENERAL_PROBLEM);
            }
        }
    }

    private Task getTaskData(Connection connection, Integer id) throws SQLException{
        String requestGetTaskData = builder.selectAllFromTable(TableParameters.TASK_TABLE_NAME)
                                            .where(TableParameters.TASK_ID)
                                            .build();
        PreparedStatement statementGetTaskData = connection.prepareStatement(requestGetTaskData);
        statementGetTaskData.setInt(1, id);
        savedStatement += statementGetTaskData.toString();
        ResultSet set = statementGetTaskData.executeQuery();
        set.next();
        Task taskFromDB = extractDataFromResultSet(set);
        set.close();
        builder.clear();
        return taskFromDB;
    }

    private void updateTaskData(Connection connection, Task task) throws SQLException{
        List<String> fieldNames = Arrays.asList(TableParameters.TASK_STATUS, TableParameters.TASK_SPENT_TIME,
                                                    TableParameters.TASK_APPROVAL_STATE);
        String requestUpdateTaskData = builder.update(TableParameters.TASK_TABLE_NAME, fieldNames)
                                              .where(TableParameters.TASK_ID)
                                              .build();
        PreparedStatement statementUpdateTaskData = connection.prepareStatement(requestUpdateTaskData);
        statementUpdateTaskData.setString(1, task.getStatus().name().toLowerCase());
        statementUpdateTaskData.setInt(2, task.getSpentTime());
        statementUpdateTaskData.setString(3, task.getApprovalState().name().toLowerCase());
        statementUpdateTaskData.setInt(4, task.getId());
        savedStatement = statementUpdateTaskData.toString();
        statementUpdateTaskData.executeUpdate();
        statementUpdateTaskData.close();
    }

    @Override
    public void delete(Integer id) {
        builder.clear();
        String request = builder.delete(TableParameters.TASK_TABLE_NAME)
                                .where(TableParameters.TASK_ID)
                                .build();
        try (Connection connection = ConnectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(request)){
            statement.setInt(1, id);
            savedStatement = statement.toString();
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(LogMessageHolder.recordDeletingInTableProblem(TableParameters.TASK_TABLE_NAME,
                                                                                        savedStatement), e);
            throw new RuntimeException(ExceptionMessages.SQL_GENERAL_PROBLEM);
        }
    }

    @Override
    public void setStatusById(Integer id, String status) {
        builder.clear();
        String request = builder.updateOne(TableParameters.TASK_TABLE_NAME, TableParameters.TASK_STATUS)
                                .where(TableParameters.TASK_ID)
                                .build();
        try (Connection connection = ConnectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(request)){
            statement.setString(1, status);
            statement.setInt(2, id);
            savedStatement = statement.toString();
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(LogMessageHolder.recordUpdatingInTableProblem(TableParameters.TASK_TABLE_NAME,
                                                                                        savedStatement), e);
            throw new RuntimeException(ExceptionMessages.SQL_GENERAL_PROBLEM);
        }
    }

    private List<String> getFieldNames() {
        return Arrays.asList(TableParameters.TASK_PROJECT_ID, TableParameters.TASK_EMPLOYEE_ID,
                TableParameters.TASK_NAME, TableParameters.TASK_STATUS, TableParameters.TASK_DEADLINE,
                TableParameters.TASK_SPENT_TIME, TableParameters.TASK_APPROVAL_STATE);
    }
}