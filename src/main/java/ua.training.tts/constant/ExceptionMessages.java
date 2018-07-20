package ua.training.tts.constant;

public interface ExceptionMessages {

    String CONNECTION_PROBLEM = "There is some problem with connection to database. Try again later on.";
    String DAO_CREATION_PROBLEM = "There is some problem with DAO creation. Try again later on,";
    String SQL_GENERAL_PROBLEM = "There is some problem with interaction with SQL. Try again later on";
    String UNIQUE = "UNIQUE";
    String EMPTY_RESULT_SET = "empty result set";
    String NOT_UNIQUE_LOGIN = "Not unique login";
    String CANNOT_ADD_UPDATE_CHILD_ROW = "Cannot add or update a child row";
    String BAD_REGISTRATION_DATA = "Not correct registration data";
    String BAD_NEW_PROJECT_DATA = "Not correct new project request's data";
    String BAD_UPDATE_PROJECT_DATA = "Not correct update project request's data";
    String BAD_NEW_TASK_DATA = "Not correct new task request's data";
    String BAD_UPDATE_TASK_DATA = "Not correct update task request's data";
    String ILLEGAL_OPERATION_ON_EMPTY_RESULT_SET = "Illegal operation on empty result set.";
    String TRIED_TO_ARCHIVE_EMPTY_PROJECT = "Sorry, but you can't archive empty project that doesn't have any tasks. Either assign some task or just delete it.";
    String PROJECT_IS_ABSCENT = "Seems that project you are trying to use doesn't exist in the database anymore.";
}
