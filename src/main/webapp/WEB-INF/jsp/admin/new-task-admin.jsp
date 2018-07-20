<%@ include file="../jspParts/general.jsp" %>
<%@ include file="../jspParts/header-admin.jsp"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib uri="http://localhost:8888/company/tags" prefix="dl"%>

<div class="main">
    <h2><fmt:message key="admin.newtask.title" /></h2>

    <c:if test = "${not empty requestScope.task_request_ok}">
        <br>
        <h5><fmt:message key="admin.newtask.message.updateOk" /></h5>
    </c:if>

    <c:if test = "${not empty requestScope.bad_task_request_data}">
        <br>
        <h5 style="color:red;"><fmt:message key="admin.newtask.message.incorrectData" /></h5>
    </c:if>

    <dl:today/>

    <form method="post" action="new_task" name="task_creation_form">

        <li>
            <label for="Project"><fmt:message key="admin.newtask.project" />:</label>
                <select name="project_id" id="project_id" required onChange = "getDeadline()">

                    <option value="0" selected><fmt:message key="admin.newtask.selectProject" /></option>
                    <c:forEach var="project" items="${active_projects}">

                        <option value="${project.id}" >
                        ${project.name} <fmt:message key="admin.newtask.deadline" />: ${project.deadline}
                        </option>

                    </c:forEach>

                </select>

        </li>

        <li>
            <label for="name"><fmt:message key="admin.newtask.name" />:</label>
            <input type="text" name="task_name" required pattern=".+" />
        </li>

        <li>
            <label for="deadline"><fmt:message key="admin.newtask.deadline" />:</label>
            <input type="date" name="task_deadline" id="deadline" min="${today}" value=""/>
        </li>

        <li>
            <label for="employee"><fmt:message key="admin.newtask.employee" />:</label>
                <select name="employee_id" id="employee_id" required >

                    <option value="0"  selected><fmt:message key="admin.newtask.selectEmployee" /></option>
                    <c:forEach var="employee" items="${employees}">

                        <option value="${employee.id}" >
                        ${employee.id} : ${employee.name} ${employee.surname} : ${employee.accountRole}
                        </option>

                    </c:forEach>

                </select>

            <input type="hidden" name="task_status" value="assigned"/>
            <input type="hidden" name="task_approval_state" value="approved"/>
            <input type="hidden" name="task_spent_time" value="0"/>
        </li>
    <br>
    <button class="submit" type="submit" onclick="return checkFunction();">
        <fmt:message key="admin.newtask.button.create" />
    </button>

<script>

function getDeadline(){
    var projectId = 0;
    projectId = document.getElementById('project_id').value;

    <c:forEach var="project" items="${active_projects}">
        var b = <c:out value="${project.id}" />
        if (b == projectId) {
            var c = "<c:out value='${project.deadline}' />";
        }
    </c:forEach>
    document.getElementById("deadline").setAttribute("value", c);
}

function checkFunction() {
    if (document.getElementById('project_id').value == 0 || document.getElementById('employee_id').value == 0) {
        alert("<fmt:message key="admin.newtask.message.chooseSmth" />");
        return false;
    }
    else {
        return true;
    }
}
</script>


    </form>

</div>

<jsp:include page="../jspParts/footer.jsp"/>