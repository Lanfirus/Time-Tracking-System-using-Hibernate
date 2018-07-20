package ua.training.tts.model.dao.factory;

import ua.training.tts.model.dao.EmployeeDao;
import ua.training.tts.model.dao.FullTaskDao;
import ua.training.tts.model.dao.ProjectDao;
import ua.training.tts.model.dao.TaskDao;
import ua.training.tts.model.dao.impl.EmployeeDaoMySQLImpl;
import ua.training.tts.model.dao.impl.FullTaskDaoMySQLImpl;
import ua.training.tts.model.dao.impl.ProjectDaoMySQLImpl;
import ua.training.tts.model.dao.impl.TaskDaoMySQLImpl;
import ua.training.tts.model.util.RequestBuilder;


public class JDBCDaoFactoryImpl extends DaoFactory {

    @Override
    public EmployeeDao createEmployeeDao() {
        return new EmployeeDaoMySQLImpl(new RequestBuilder());
    }

    @Override
    public TaskDao createTaskDao() {
        return new TaskDaoMySQLImpl(new RequestBuilder());
    }

    @Override
    public ProjectDao createProjectDao() {
        return new ProjectDaoMySQLImpl(new RequestBuilder());
    }

    @Override
    public FullTaskDao createFullTaskDao() {
        return new FullTaskDaoMySQLImpl(new RequestBuilder());
    }
}