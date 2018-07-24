package ua.training.tts.util;

import ua.training.tts.constant.LogMessages;
import ua.training.tts.model.entity.Employee;

/**
 * Stores templates for messages to be put into logs.
 */
public class LogMessageHolder {

    public static String userLogin(String login, String password, String role) {
        StringBuilder builder = new StringBuilder();
        builder.append(LogMessages.USER_WITH_LOGIN)
               .append(login)
               .append(LogMessages.AND_PASSWORD)
               .append(password)
               .append((Employee.AccountRole.UNKNOWN.name().equalsIgnoreCase(role)) ? LogMessages.TRIED_TO
                                                                                    : LogMessages.EMPTY)
               .append(LogMessages.LOGIN_TO_SYSTEM);
        return builder.toString();
    }

    public static String userReenter(String login, String password, String role) {
        StringBuilder builder = new StringBuilder();
        builder.append(LogMessages.USER_WITH_LOGIN)
                .append(login)
                .append(LogMessages.AND_PASSWORD)
                .append(password)
                .append(LogMessages.REENTER_SITE);
        return builder.toString();
    }

    public static String userLogout(String login) {
        StringBuilder builder = new StringBuilder();
        builder.append(LogMessages.USER_WITH_LOGIN)
                .append(login)
                .append(LogMessages.LOGOUT_FROM_SYSTEM);
        return builder.toString();
    }

    public static String userRegistrationSuccessful(String login) {
        StringBuilder builder = new StringBuilder();
        builder.append(LogMessages.USER_WITH_LOGIN)
                .append(login)
                .append(LogMessages.REGISTERED_SUCCESSFULLY);
        return builder.toString();
    }

    public static String userUsedExistingLogin(String login) {
        StringBuilder builder = new StringBuilder();
        builder.append(LogMessages.USER_WITH_LOGIN)
                .append(login)
                .append(LogMessages.USED_EXISTING_LOGIN);
        return builder.toString();
    }

    public static String userUsedNotExistingCredentials(String login, String password) {
        StringBuilder builder = new StringBuilder();
        builder.append(LogMessages.USER_WITH_LOGIN)
                .append(login)
                .append(LogMessages.AND_PASSWORD)
                .append(password)
                .append(LogMessages.TRIED_TO)
                .append(LogMessages.REGISTER_IN_SYSTEM);
        return builder.toString();
    }

    public static String getConnectionProblem(){
        StringBuilder builder = new StringBuilder();
        builder.append(LogMessages.GET_CONNECTION_PROBLEM);
        return builder.toString();
    }

    public static String recordInsertionToTableProblem(String tableName, String statement){
        StringBuilder builder = new StringBuilder();
        builder.append(LogMessages.PROBLEM_OCCURED_DURING)
                .append(LogMessages.RECORD_INSERTION)
                .append(LogMessages.INTO_TABLE)
                .append(tableName)
                .append(LogMessages.WITH_STATEMENT)
                .append(statement);
        return builder.toString();
    }

    public static String recordSearchingInTableProblem(String tableName, String statement){
        StringBuilder builder = new StringBuilder();
        builder.append(LogMessages.PROBLEM_OCCURED_DURING)
                .append(LogMessages.RECORD_SEARCHING)
                .append(LogMessages.INTO_TABLE)
                .append(tableName)
                .append(LogMessages.WITH_STATEMENT)
                .append(statement);
        return builder.toString();
    }

    public static String recordUpdatingInTableProblem(String tableName, String statement){
        StringBuilder builder = new StringBuilder();
        builder.append(LogMessages.PROBLEM_OCCURED_DURING)
                .append(LogMessages.RECORD_UPDATING)
                .append(LogMessages.INTO_TABLE)
                .append(tableName)
                .append(LogMessages.WITH_STATEMENT)
                .append(statement);
        return builder.toString();
    }

    public static String recordDeletingInTableProblem(String tableName, String statement){
        StringBuilder builder = new StringBuilder();
        builder.append(LogMessages.PROBLEM_OCCURED_DURING)
                .append(LogMessages.RECORD_DELETING)
                .append(LogMessages.INTO_TABLE)
                .append(tableName)
                .append(LogMessages.WITH_STATEMENT)
                .append(statement);
        return builder.toString();
    }

    public static String userDoubleLogin(String login) {
        StringBuilder builder = new StringBuilder();
        builder.append(LogMessages.USER_WITH_LOGIN)
                .append(login)
                .append(LogMessages.TRIED_TO)
                .append(LogMessages.LOGIN_SECOND_TIME);
        return builder.toString();
    }

    public static String userProfileUpdateSuccessful(String login) {
        StringBuilder builder = new StringBuilder();
        builder.append(LogMessages.USER_WITH_LOGIN)
                .append(login)
                .append(LogMessages.UPDATED_PROFILE_SUCCESSFULLY);
        return builder.toString();
    }
}
