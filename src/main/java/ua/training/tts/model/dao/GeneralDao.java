package ua.training.tts.model.dao;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import ua.training.tts.controller.util.HibernateUtil;

import java.util.List;

public interface GeneralDao<T, ID>{

    Logger log = Logger.getRootLogger();

    /**
     * Creates entry in database
     * @param entity
     */
    default void create(T entity){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Returns entry from database by id
     * @param id
     */
    T findById(ID id);

    /**
     * Returns all entries from database
     * @return result list
     */
    List<T> findAll();

    /**
     * Updates entry in database
     * @param entity
     */
    default void update(T entity){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.merge(entity);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Deletes entry in database by id
     * @param id
     */
    void delete(ID id);
}
