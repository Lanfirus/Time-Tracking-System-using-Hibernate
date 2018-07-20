<%@ include file="../jspParts/general.jsp" %>
<%@ include file="../jspParts/header-employee.jsp"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib uri="http://localhost:8888/company/tags" prefix="dl"%>

<div class="main">
    <h2><fmt:message key="employee.newtask.title" /></h2>

    <c:if test = "${not empty requestScope.task_request_ok}">
        <br>
        <h5><fmt:message key="employee.newtask.message.requestOk" /></h5>
    </c:if>

    <c:if test = "${not empty requestScope.task_data_changed}">
        <br>
        <h5 style="color:red;"><fmt:message key="employee.newtask.message.dataChanged" /></h5>
    </c:if>

    <dl:today/>

    <form method="post" action="request_new_task" name="my_tasks_form">

        <li>
            <label for="Project"><fmt:message key="employee.newtask.project" />:</label>

                <select name="project_id" id="project_id" required onChange = "getDeadline()">

                    <option value="0" selected><fmt:message key="employee.newtask.selectProject" /></option>
                    <c:forEach var="project" items="${myProjects}">

                        <option value="${project.projectId}" >
                        ${project.projectName} <fmt:message key="employee.newtask.deadline" />: ${project.projectDeadline}
                        </option>

                    </c:forEach>

                </select>

        </li>

        <li>
            <label for="name"><fmt:message key="employee.newtask.name" />:</label>
            <input type="text" name="task_name" required pattern=".+" />
        </li>

        <li>
            <label for="deadline"><fmt:message key="employee.newtask.deadline" />:</label>
            <input type="date" name="task_deadline" id="deadline" min="${today}" value=""/>
        </li>

        <li>
            <label for="spent_time"><fmt:message key="employee.newtask.spentTime" />:</label>
            <input type="number" style="width: 5em" name="task_spent_time" min = "0" value = "0"/>
        </li>
    <br>
    <button class="submit" type="submit" onclick="return checkFunction();">
        <fmt:message key="employee.newtask.button.requestNew" />
    </button>

<script>

function getDeadline(){
    var projectId = 0;
    projectId = document.getElementById('project_id').value;

    <c:forEach var="project" items="${myProjects}">
        var a = "<c:out value='${project.projectDeadline}' />";
        var b = <c:out value="${project.projectId}" />
        if (b == projectId) {
            var c = a;
        }
    </c:forEach>
    document.getElementById("deadline").setAttribute("value", c);
    }

function checkFunction() {
    if (document.getElementById('project_id').value == 0) {
        alert("You have not chosen project for task!");
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