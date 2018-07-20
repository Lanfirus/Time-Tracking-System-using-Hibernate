<%@ include file="../jspParts/general.jsp" %>
<%@ include file="../jspParts/header-admin.jsp"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

<div class="main" style="overflow-x: auto">
    <h2><fmt:message key="employeeInformation.title" /></h2>

    <form method="post" action="" name="employee_information_form">

        <display:table name="employees" id="employee" sort="list" requestURI="" pagesize="5">

            <display:column property="id" titleKey="employeeInformation.table.id"
                sortable="true" headerClass="sortable" />

            <display:column property="login" titleKey="employeeInformation.table.login"
                sortable="true" headerClass="sortable" />

            <display:column property="name" titleKey="employeeInformation.table.name"
                sortable="true" headerClass="sortable" />

            <display:column property="surname" titleKey="employeeInformation.table.surname"
                sortable="true" headerClass="sortable" />

            <display:column property="patronymic" titleKey="employeeInformation.table.patronymic"
                sortable="true" headerClass="sortable" />

            <display:column property="email" titleKey="employeeInformation.table.email"
                sortable="true" headerClass="sortable" />

            <display:column property="mobilePhone" titleKey="employeeInformation.table.mobilePhone"
                sortable="true" headerClass="sortable" />

            <display:column property="comment" titleKey="employeeInformation.table.comment"
                sortable="true" headerClass="sortable" />

            <display:column titleKey="employeeInformation.table.accountRole" sortable="true" headerClass="sortable">
                <select name="e_account_role${employee.id}">
                                        <option value="employee" ${employee.accountRole == 'EMPLOYEE' ? 'selected' : ''}>
                                            <fmt:message key="registration.accountRole.employee" />
                                        </option>

                                        <option value="admin" ${employee.accountRole == 'ADMIN' ? 'selected' : ''}>
                                            <fmt:message key="registration.accountRole.admin" />
                                        </option>
                                    </select>
            </display:column>

            <display:column titleKey="employeeInformation.table.select" >
                    <input type="radio" name="employee_id" value="${employee.id}" checked>
                </display:column>

            </display:table>

            <nav>
                <ul style="display: flex; padding-left: 0;" >
                    <button class="submit" type="submit" style="margin: 3px"
                        onClick="return check(this);" name="changeRole" >
                        <fmt:message key="employeeInformation.table.button.changeRole" />
                    </button>
                    <br>
                    <button class="submit" type="submit" style="margin: 3px"
                        onClick="return check(this);" name="delete" >
                        <fmt:message key="employeeInformation.table.button.delete" />
                    </button>
                </ul>
            </nav>

    </form>

</div>

<script type="text/javascript">
function check(button) {
    <c:if test="${empty employees}">
        return false;
    </c:if>
    var form = button.form;
    if(button.name == "changeRole") {
        form.action = "employee_change_role";
    }
    else {
        form.action = "employee_delete";
    }
    return true;
}
</script>

<jsp:include page="../jspParts/footer.jsp"/>