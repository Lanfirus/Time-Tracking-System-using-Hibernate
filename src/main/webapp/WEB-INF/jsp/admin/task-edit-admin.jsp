<%@ include file="../jspParts/general.jsp" %>
<%@ include file="../jspParts/header-admin.jsp"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib uri="http://localhost:8888/company/tags" prefix="dl"%>

<div class="main">
    <h2><fmt:message key="admin.taskedit.title" /></h2>

    <c:if test = "${not empty requestScope.bad_task_edit_data}">
        <br>
        <h5 style="color:red;"><fmt:message key="admin.taskedit.message.incorrectData" /></h5>
    </c:if>

    <dl:today/>

    <form method="post" action="task_edit" name="task_edit_form">

        <li>
            <label for="id"><fmt:message key="admin.taskedit.id" />:</label>
            <c:out value="${task.id}" />
            <input type="hidden" style="width: 9em" name="task_id" value="${task.id}"/>
        </li>

        <li>
            <label for="Project"><fmt:message key="admin.taskedit.project" />:</label>
                <select name="project_id" id="project_id" required >

                    <option value="0" selected><fmt:message key="admin.taskedit.selectProject" /></option>
                    <c:forEach var="project" items="${active_projects}">

                        <option value="${project.id}" ${project.id == task.projectId ? 'selected' : ''}>
                        ${project.name} <fmt:message key="admin.taskedit.deadline" />: ${project.deadline}
                        </option>

                    </c:forEach>

                </select>

        </li>

        <li>
                <label for="Status"><fmt:message key="admin.taskedit.status" />:</label>
                <select name="task_status">
                    <option value="assigned" ${task.status == 'ASSIGNED' ? 'selected' : ''}>
                        <fmt:message key="admin.taskedit.status.assigned" />
                    </option>

                    <option value="finished" ${task.status == 'FINISHED' ? 'selected' : ''}>
                        <fmt:message key="admin.taskedit.status.finished" />
                    </option>

                    <option value="cancelled" ${task.status == 'CANCELLED' ? 'selected' : ''}>
                        <fmt:message key="admin.taskedit.status.cancelled" />
                    </option>
                </select>
        </li>

        <li>
                <label for="ApprovalState"><fmt:message key="admin.taskedit.approvalState" />:</label>
                <select name="task_approval_state">
                    <option value="approved" ${task.approvalState == 'APPROVED' ? 'selected' : ''}>
                        <fmt:message key="admin.taskedit.approvalState.approved" />
                    </option>

                    <option value="not_approved" ${task.approvalState == 'NOT_APPROVED' ? 'selected' : ''}>
                        <fmt:message key="admin.taskedit.approvalState.notApproved" />
                    </option>
                </select>
                </li>

        <li>
            <label for="name"><fmt:message key="admin.taskedit.name" />:</label>
            <input type="text" name="task_name" required pattern=".+" value="${task.name}" />
        </li>

        <li>
            <label for="deadline"><fmt:message key="admin.taskedit.deadline" />:</label>
            <input type="date" name="task_deadline" id="deadline" min="${today}" value="${task.deadline}"/>
        </li>

        <li>
            <label for="spent_time"><fmt:message key="admin.taskedit.spentTime" />:</label>
            <c:out value="${task.spentTime}" />
            <input type="hidden" style="width: 5em" name="task_spent_time" value="${task.spentTime}"/>
        </li>

        <li>
            <label for="assigned_employee"><fmt:message key="admin.taskedit.employee" />:</label>
            <c:out value="${task.employeeId}" />
            <input type="hidden" style="width: 9em" name="employee_id" value="${task.employeeId}"/>
        </li>
    <br>
    <button class="submit" type="submit" onclick="return checkFunction();">
        <fmt:message key="admin.taskedit.button.edit" />
    </button>

<script>
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