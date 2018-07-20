package ua.training.tts.dao.connectionpool;

import org.junit.Assert;
import org.junit.Test;
import ua.training.tts.model.dao.connectionpool.ConnectionPool;

import java.sql.Connection;

public class ConnectionPoolTest extends Assert{

    @Test
    public void getConnection(){
        Connection actuals = ConnectionPool.getConnection();
        assertTrue( actuals instanceof Connection);
    }
}
