package ua.training.tts.model.dao;

import org.apache.log4j.Logger;
import ua.training.tts.model.util.RequestBuilder;

import java.util.List;

public interface GeneralDao<T, ID>{

    Logger log = Logger.getRootLogger();

    /**
     * Creates entry in database
     * @param entity
     */
    void create(T entity);

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
    void update(T entity);

    /**
     * Deletes entry in database by id
     * @param id
     */
    void delete(ID id);
}
