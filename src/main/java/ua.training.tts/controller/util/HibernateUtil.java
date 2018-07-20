package ua.training.tts.controller.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.MySQL57Dialect;
import ua.training.tts.constant.DBParameters;
import ua.training.tts.model.entity.Employee;
import ua.training.tts.model.entity.Project;
import ua.training.tts.model.entity.Task;
import ua.training.tts.model.entity.full.FullTask;

import java.util.Properties;

public class HibernateUtil {

    private static final SessionFactory SESSION_FACTORY = initializeStaticFactory();

    public static Session getSession() {
        return SESSION_FACTORY.openSession();
    }

    private static SessionFactory initializeStaticFactory(){
        Properties jpaProps = new Properties();
        jpaProps.put(Environment.DIALECT, MySQL57Dialect.class);
        jpaProps.put(Environment.URL, DBParameters.URL_CUSTOM);
        jpaProps.put(Environment.USER, DBParameters.NAME);
        jpaProps.put(Environment.PASS, DBParameters.PASSWORD);
        jpaProps.put(Environment.HBM2DDL_AUTO, "none");
        jpaProps.put(Environment.SHOW_SQL, true);

        Configuration configuration = new Configuration()
                .addAnnotatedClass(Employee.class)
                .addAnnotatedClass(Project.class)
                .addAnnotatedClass(Task.class)
                .addAnnotatedClass(FullTask.class)
                .addProperties(jpaProps);

        return configuration.buildSessionFactory();
    }
}
