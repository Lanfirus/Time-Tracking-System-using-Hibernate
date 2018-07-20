package ua.training.tts.constant;

import ua.training.tts.model.entity.Employee;

public interface TestConstants {

    boolean TRUE = true;
    boolean FALSE = false;

    int ROW_COUNT = 8;
    Integer ID = 0;

    String TABLE = "table";
    String COLUMN = "column";
    String VALUE1 = "value1";
    String VALUE2 = "value2";
    String LOGIN = "login";
    String PASSWORD = "password";
    String NAME = "Name";
    String SURNAME = "Surname";
    String PATRONYMIC = "Patronymic";
    String EMAIL = "email@ua.ua";
    String MOBILE_PHONE = "380441234567";
    String COMMENT = "comment";
    String NULL = null;
    String INCORRECT_REQUEST = "incorrect_request";
    String LANGUAGE = "language";
    String ANOTHER_LANGUAGE = "another_language";
    String SOME_REQUESTED_URL = "requested_url";
    String PAGE = "page";

    String INSERT_INTO_TABLE_EXPECTED = "INSERT INTO `TABLE` ";
    String INSERT_VALUES_EXPECTED = "(`value1`,`value2`) VALUES (?,?)";
    String SELECT_ALL_FROM_TABLE_EXPECTED = "select * from table";
    String WHERE_EXPECTED = " where column = ?";
    String AND_EXPECTED = " and column = ?";
    String UPDATE_EXPECTED = "update table set value1 = ?,value2 = ? ";
    String DELETE = "delete from column ";

    String LOGIN_CHECK_DOUBLE_LOGIN_METHOD = "checkDoubleLogin";
    String MAIN_SERVLET_SEND_USER_TO_PAGE = "sendUserToPage";
    String MAIN_SERVLET_GET_COMMAND = "getCommand";

    Employee.AccountRole ACCOUNT_ROLE = Employee.AccountRole.EMPLOYEE;
}
