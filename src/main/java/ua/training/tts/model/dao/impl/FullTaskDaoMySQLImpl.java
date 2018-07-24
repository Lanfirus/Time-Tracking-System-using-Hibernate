package ua.training.tts.model.dao.impl;

import ua.training.tts.constant.ExceptionMessages;
import ua.training.tts.constant.model.dao.TableParameters;
import ua.training.tts.model.dao.FullTaskDao;
import ua.training.tts.model.dao.connectionpool.ConnectionPool;
import ua.training.tts.model.entity.Project;
import ua.training.tts.model.entity.full.FullTask;
import ua.training.tts.model.util.RequestBuilder;
import ua.training.tts.model.util.builder.FullTaskBuilder;
import ua.training.tts.util.LogMessageHolder;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * MySQL implementation of FullTask entity DAO
 */
public class FullTaskDaoMySQLImpl implements FullTaskDao {

    private RequestBuilder builder;
    private String savedStatement;
    private StringBuilder buildedStatement;

    public FullTaskDaoMySQLImpl(RequestBuilder builder){
        this.builder = builder;
    }

    @Override
    public List<FullTask> findAll() {
        builder.clear();
        List<FullTask> resultList = new ArrayList<>();
        String request = builder.selectAllFromTable(TableParameters.TASK_TABLE_NAME)
                                .join(TableParameters.PROJECT_TABLE_NAME)
                                .using(TableParameters.PROJECT_ID)
                                .join(TableParameters.EMPLOYEE_TABLE_NAME)
                                .join(TableParameters.EMPLOYEE_ID)
                                .build();
        try (Connection connection = ConnectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(request)){
            savedStatement = statement.toString();
            ResultSet set = statement.executeQuery();
            while (set.next()){
                FullTask result = extractDataFromResultSet(set);
                resultList.add(result);
            }
        } catch (SQLException e) {
            log.error(LogMessageHolder.recordSearchingInTableProblem(TableParameters.PROJECT_TABLE_NAME,
                                                                                            savedStatement), e);
            throw new RuntimeException(ExceptionMessages.SQL_GENERAL_PROBLEM);
        }
        return resultList;
    }

    /**
     * Builds FullTask entity from data gotten from database
     *
     * @param set       Result set with data from database
     * @return          FullTask entity filled with data from database
     * @throws SQLException
     */
    private FullTask extractDataFromResultSet(ResultSet set) throws SQLException {
        FullTaskBuilder builder = new FullTaskBuilder();
        FullTask fullTask = builder.setTaskId(set.getInt(TableParameters.TASK_ID))
                .setTaskName(set.getString(TableParameters.TASK_NAME))
                .setTaskDeadline(set.getDate(TableParameters.TASK_DEADLINE).toLocalDate())
                .setTaskSpentTime(set.getInt(TableParameters.TASK_SPENT_TIME))
                .setTaskStatus(set.getString(TableParameters.TASK_STATUS))
                .setTaskState(set.getString(TableParameters.TASK_APPROVAL_STATE))

                .setEmployeeId(set.getInt(TableParameters.EMPLOYEE_ID))
                .setEmployeeLogin(set.getString(TableParameters.EMPLOYEE_LOGIN))
                .setEmployeePassword(set.getString(TableParameters.EMPLOYEE_PASSWORD))
                .setEmployeeName(set.getString(TableParameters.EMPLOYEE_NAME))
                .setEmployeeSurname(set.getString(TableParameters.EMPLOYEE_SURNAME))
                .setEmployeePatronymic(set.getString(TableParameters.EMPLOYEE_PATRONYMIC))
                .setEmployeeEmail(set.getString(TableParameters.EMPLOYEE_EMAIL))
                .setEmployeeMobilePhone(set.getString(TableParameters.EMPLOYEE_MOBILE_PHONE))
                .setEmployeeComment(set.getString(TableParameters.EMPLOYEE_COMMENT))
                .setEmployeeAccountRole(set.getString(TableParameters.EMPLOYEE_ACCOUNT_ROLE))

                .setProjectId(set.getInt(TableParameters.PROJECT_ID))
                .setProjectName(set.getString(TableParameters.PROJECT_NAME))
                .setProjectDeadline(set.getDate(TableParameters.PROJECT_DEADLINE).toLocalDate())
                .setProjectStatus(set.getString(TableParameters.PROJECT_STATUS))

                .buildFullTask();
        return fullTask;
    }

    /**
     * Searches all project that are assigned to provided Employee.
     * Project entity doesn't have relation to Employees, so it's necessary to get such information through
     * FullTask entity.
     *
     * @param id        Employee Id
     * @return          List of FullTask entities filled with project data from database
     */
    @Override
    public List<FullTask> findAllProjectsByEmployeeId(Integer id) {
        builder.clear();
        List<FullTask> resultList = new ArrayList<>();
        List<String> columnNames = Arrays.asList(TableParameters.PROJECT_ID, TableParameters.PROJECT_NAME,
                TableParameters.PROJECT_DEADLINE, TableParameters.PROJECT_STATUS);
        String request = builder.selectSomeFromTableDistinct(TableParameters.TASK_TABLE_NAME, columnNames)
                                .join(TableParameters.PROJECT_TABLE_NAME)
                                .using(TableParameters.PROJECT_ID)
                                .where(TableParameters.EMPLOYEE_ID)
                                .and(TableParameters.PROJECT_STATUS)
                                .build();
        try (Connection connection = ConnectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(request)){
            statement.setInt(1, id);
            statement.setString(2, Project.Status.ASSIGNED.name().toLowerCase());
            savedStatement = statement.toString();
            ResultSet set = statement.executeQuery();
            while (set.next()){
                FullTask result = extractProjectDataFromResultSet(set);
                resultList.add(result);
            }
        } catch (SQLException e) {
            log.error(LogMessageHolder.recordSearchingInTableProblem(TableParameters.PROJECT_TABLE_NAME,
                                                                                        savedStatement), e);
            throw new RuntimeException(ExceptionMessages.SQL_GENERAL_PROBLEM);
        }
        return resultList;
    }

    private FullTask extractProjectDataFromResultSet(ResultSet set) throws SQLException {
        FullTaskBuilder builder = new FullTaskBuilder();
        FullTask fullTask = builder.setProjectId(set.getInt(TableParameters.PROJECT_ID))
                                    .setProjectName(set.getString(TableParameters.PROJECT_NAME))
                                    .setProjectDeadline(set.getDate(TableParameters.PROJECT_DEADLINE).toLocalDate())
                                    .setProjectStatus(set.getString(TableParameters.PROJECT_STATUS))
                                    .buildProject();
        return fullTask;
    }

    /**
     * Provides archivation functionality.
     * At first, gets all information about existing tasks related to specific project.
     * At second, puts project data into archive.
     * At third, puts task data into archive.
     * At forth, deletes project data from active project table. Task information will be deleted automatically by
     * cascade.
     * Checks that project going to be archived is not empty meaning has at least one task assigned to it.
     *
     * @param id        Id of project to be sent to archive
     */
    @Override
    public void archiveProjectAndTasks(Integer id) {

        try (Connection connection = ConnectionPool.getConnection()) {
            connection.setAutoCommit(false);
            builder.clear();
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            buildedStatement = new StringBuilder();
            List<FullTask> projectTaskData = getProjectTaskData(connection, id);
            if (projectTaskData.size() == 0) {
                throw new RuntimeException(ExceptionMessages.TRIED_TO_ARCHIVE_EMPTY_PROJECT);
            }
            putProjectDataToArchive(connection, id, projectTaskData);
            putTaskDataToArchive(connection, projectTaskData);
            deleteProject(connection, id);
            connection.commit();
            connection.setAutoCommit(true);
        }
        catch (SQLException e) {
            log.error(LogMessageHolder.recordSearchingInTableProblem(TableParameters.PROJECT_TABLE_NAME,
                                                                                      buildedStatement.toString()), e);
            throw new RuntimeException(ExceptionMessages.SQL_GENERAL_PROBLEM);
        }
    }

    private List<FullTask> getProjectTaskData(Connection connection, Integer id) throws SQLException{
        List<FullTask> projectTaskData = new ArrayList<>();
        String requestGetProjectTaskData = builder.selectAllFromTable(TableParameters.TASK_TABLE_NAME)
                                                  .join(TableParameters.PROJECT_TABLE_NAME)
                                                  .using(TableParameters.PROJECT_ID)
                                                  .where(TableParameters.PROJECT_ID)
                                                  .build();
        PreparedStatement statementGetProjectData = connection.prepareStatement(requestGetProjectTaskData);
        statementGetProjectData.setInt(1,id);
        buildedStatement.append(statementGetProjectData.toString());
        ResultSet set = statementGetProjectData.executeQuery();
        while (set.next()){
            FullTask result = extractProjectTaskDataFromResultSet(set);
            System.out.println(result);
            projectTaskData.add(result);
        }
        set.close();
        builder.clear();
        return projectTaskData;
    }

    private FullTask extractProjectTaskDataFromResultSet(ResultSet set) throws SQLException {
        FullTaskBuilder builder = new FullTaskBuilder();
        FullTask fullTask = builder.setTaskId(set.getInt(TableParameters.TASK_ID))
                .setTaskName(set.getString(TableParameters.TASK_NAME))
                .setTaskDeadline(set.getDate(TableParameters.TASK_DEADLINE).toLocalDate())
                .setTaskSpentTime(set.getInt(TableParameters.TASK_SPENT_TIME))
                .setTaskStatus(set.getString(TableParameters.TASK_STATUS))
                .setTaskState(set.getString(TableParameters.TASK_APPROVAL_STATE))
                .setEmployeeId(set.getInt(TableParameters.EMPLOYEE_ID))

                .setProjectId(set.getInt(TableParameters.PROJECT_ID))
                .setProjectName(set.getString(TableParameters.PROJECT_NAME))
                .setProjectDeadline(set.getDate(TableParameters.PROJECT_DEADLINE).toLocalDate())
                .setProjectStatus(set.getString(TableParameters.PROJECT_STATUS))

                .buildFullTask();
        return fullTask;
    }

    private void putProjectDataToArchive(Connection connection, Integer id, List<FullTask> projectData)
            throws SQLException{
        String requestPutProjectDataToArchive = builder.insertIntoTable(TableParameters.PROJECT_ARCHIVE_TABLE_NAME)
                                                       .insertValueNames(getProjectFieldNames())
                                                       .build();
        PreparedStatement statementPutProjectDataToArchive = connection.prepareStatement(requestPutProjectDataToArchive);
        statementPutProjectDataToArchive.setInt(1, id);
        statementPutProjectDataToArchive.setString(2, projectData.get(0).getProjectName());
        statementPutProjectDataToArchive.setDate(3, Date.valueOf(projectData.get(0).getProjectDeadline()));
        statementPutProjectDataToArchive.setString(4, projectData.get(0)
                                                                             .getProjectStatus()
                                                                             .name()
                                                                             .toLowerCase());
        buildedStatement.append(statementPutProjectDataToArchive.toString());
        statementPutProjectDataToArchive.executeUpdate();
        builder.clear();
    }

    private void putTaskDataToArchive(Connection connection, List<FullTask> taskData)
            throws SQLException{
        for (int i = 0; i < taskData.size(); i++) {
            String requestPutTaskDataToArchive = builder.insertIntoTable(TableParameters.TASK_ARCHIVE_TABLE_NAME)
                                                        .insertValueNames(getTaskFieldNames())
                                                        .build();
            PreparedStatement statementPutTaskDataToArchive = connection.prepareStatement(requestPutTaskDataToArchive);
            statementPutTaskDataToArchive.setInt(1, taskData.get(i).getTaskId());
            statementPutTaskDataToArchive.setInt(2, taskData.get(i).getProjectId());
            statementPutTaskDataToArchive.setInt(3, taskData.get(i).getEmployeeId());
            statementPutTaskDataToArchive.setString(4, taskData.get(i).getTaskName());
            statementPutTaskDataToArchive.setString(5, taskData.get(i).getTaskStatus().name().toLowerCase());
            statementPutTaskDataToArchive.setDate(6, Date.valueOf(taskData.get(i).getTaskDeadline()));
            statementPutTaskDataToArchive.setInt(7, taskData.get(i).getTaskSpentTime());
            statementPutTaskDataToArchive.setString(8, taskData.get(i).getTaskApprovalState().name().toLowerCase());
            buildedStatement.append(statementPutTaskDataToArchive.toString());
            statementPutTaskDataToArchive.executeUpdate();
            builder.clear();
        }
    }

    private void deleteProject(Connection connection, Integer id) throws SQLException{
        String requestDeleteProject = builder.delete(TableParameters.PROJECT_TABLE_NAME)
                                             .where(TableParameters.PROJECT_ID)
                                             .build();
        PreparedStatement statementDeleteProject = connection.prepareStatement(requestDeleteProject);
        statementDeleteProject.setInt(1, id);
        buildedStatement.append(statementDeleteProject.toString());
        statementDeleteProject.executeUpdate();
        builder.clear();
        statementDeleteProject.close();
    }

    private List<String> getProjectFieldNames() {
        return Arrays.asList(TableParameters.PROJECT_ID, TableParameters.PROJECT_NAME, TableParameters.PROJECT_DEADLINE,
                TableParameters.PROJECT_STATUS);
    }

    private List<String> getTaskFieldNames() {
        return Arrays.asList(TableParameters.TASK_ID, TableParameters.TASK_PROJECT_ID, TableParameters.TASK_EMPLOYEE_ID,
                TableParameters.TASK_NAME, TableParameters.TASK_STATUS, TableParameters.TASK_DEADLINE,
                TableParameters.TASK_SPENT_TIME, TableParameters.TASK_APPROVAL_STATE);
    }

    @Override
    public void create(FullTask entity) {
    }

    @Override
    public FullTask findById(Integer integer) {
        return null;
    }

    @Override
    public void update(FullTask fullTask) {
    }

    @Override
    public void delete(Integer integer) {
    }
}