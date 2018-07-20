package ua.training.tts.constant;

public interface DBParameters {

    String URL_CUSTOM = "jdbc:mysql://localhost/time_tracking?characterEncoding=utf8&verifyServerCertificate=false&useSSL=true";
    String NAME = "admin";
    String PASSWORD = "admin";

    String CACHE_PREPARED_STATEMENT = "cachePrepStmts";
    String CACHE_PREPARED_STATEMENT_VALUE = "false";
    String CACHE_SIZE_PREPARED_STATEMENT = "prepStmtCacheSize";
    String CACHE_SIZE_PREPARED_STATEMENT_VALUE = "25";
    String CACHE_SQL_LIMIT_PREPARED_STATEMENT = "prepStmtCacheSqlLimit";
    String CACHE_SQL_LIMIT_PREPARED_STATEMENT_VALUE = "256";
}
