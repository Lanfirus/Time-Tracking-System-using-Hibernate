package ua.training.tts.constant.model.dao;

public interface TableParameters {

    String EMPLOYEE_TABLE_NAME = "employee";
    String PROJECT_TABLE_NAME = "project";
    String TASK_TABLE_NAME = "task";
    String PROJECT_ARCHIVE_TABLE_NAME = "project_archive";
    String TASK_ARCHIVE_TABLE_NAME = "task_archive";

    String EMPLOYEE_ID = "employee_id";
    String EMPLOYEE_LOGIN = "employee_login";
    String EMPLOYEE_PASSWORD = "employee_password";
    String EMPLOYEE_NAME = "employee_name";
    String EMPLOYEE_SURNAME = "employee_surname";
    String EMPLOYEE_PATRONYMIC = "employee_patronymic";
    String EMPLOYEE_EMAIL = "employee_email";
    String EMPLOYEE_MOBILE_PHONE = "employee_mobile_phone";
    String EMPLOYEE_COMMENT = "employee_comment";
    String EMPLOYEE_ACCOUNT_ROLE = "employee_account_role";

    String TASK_ID = "task_id";
    String TASK_PROJECT_ID = "project_id";
    String TASK_EMPLOYEE_ID = "employee_id";
    String TASK_NAME = "task_name";
    String TASK_STATUS = "task_status";
    String TASK_DEADLINE = "task_deadline";
    String TASK_SPENT_TIME = "task_spent_time";
    String TASK_APPROVAL_STATE = "task_approval_state";

    String PROJECT_ID = "project_id";
    String PROJECT_NAME = "project_name";
    String PROJECT_DEADLINE = "project_deadline";
    String PROJECT_STATUS = "project_status";
}
