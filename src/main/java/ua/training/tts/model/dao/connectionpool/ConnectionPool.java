package ua.training.tts.model.dao.connectionpool;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.log4j.Logger;
import ua.training.tts.constant.DBParameters;
import ua.training.tts.constant.ExceptionMessages;
import ua.training.tts.util.LogMessageHolder;

import java.sql.Connection;

/**
 * Provides Connection pool functionality.
 * Uses HikariCP.
 */

public class ConnectionPool {

    private final static HikariConfig CONFIG = new HikariConfig();
    private final static HikariDataSource DATA_SOURCE;
    private final static Logger LOG = Logger.getRootLogger();

    static {
        CONFIG.setJdbcUrl(DBParameters.URL_CUSTOM);
        CONFIG.setUsername(DBParameters.NAME);
        CONFIG.setPassword(DBParameters.PASSWORD);
        CONFIG.addDataSourceProperty( DBParameters.CACHE_PREPARED_STATEMENT,
                DBParameters.CACHE_PREPARED_STATEMENT_VALUE);
        DATA_SOURCE = new HikariDataSource(CONFIG);
    }

    private ConnectionPool() {}

    public static Connection getConnection(){
        try {
            return DATA_SOURCE.getConnection();
        }
        catch(Exception e){
            LOG.error(LogMessageHolder.getConnectionProblem(), e);
            throw new RuntimeException(ExceptionMessages.CONNECTION_PROBLEM);
        }
    }
}
