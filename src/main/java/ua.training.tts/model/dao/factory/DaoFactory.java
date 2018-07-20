package ua.training.tts.model.dao.factory;

import ua.training.tts.model.dao.EmployeeDao;
import ua.training.tts.model.dao.FullTaskDao;
import ua.training.tts.model.dao.ProjectDao;
import ua.training.tts.model.dao.TaskDao;

public abstract class DaoFactory {

   public static DaoFactory getInstance(){
      return new JDBCDaoFactoryImpl();
   }

   public abstract EmployeeDao createEmployeeDao();

   public abstract TaskDao createTaskDao();

   public abstract ProjectDao createProjectDao();

   public abstract FullTaskDao createFullTaskDao();
}