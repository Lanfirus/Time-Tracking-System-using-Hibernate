<%@ include file="../jspParts/general.jsp" %>
<%@ include file="../jspParts/header-admin.jsp"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib uri="http://localhost:8888/company/tags" prefix="dl"%>

<div class="main">
    <h2><fmt:message key="admin.projectedit.title" /></h2>

    <form method="post" action="project_edit" name="project_edit_form">

        <li>
            <label for="id"><fmt:message key="admin.projectedit.id" />:</label>
            <c:out value="${project.id}" />
            <input type="hidden" name="project_id" value="${project.id}"/>
        </li>

        <li>
                <label for="status"><fmt:message key="admin.projectedit.status" />:</label>
                <select name="project_status">
                    <option value="assigned" ${project.status == 'ASSIGNED' ? 'selected' : ''}>
                        <fmt:message key="admin.projectedit.status.assigned" />
                    </option>

                    <option value="finished" ${project.status == 'FINISHED' ? 'selected' : ''}>
                        <fmt:message key="admin.projectedit.status.finished" />
                    </option>

                    <option value="cancelled" ${project.status == 'CANCELLED' ? 'selected' : ''}>
                        <fmt:message key="admin.projectedit.status.cancelled" />
                    </option>
                </select>
        </li>
        <li>
            <label for="name"><fmt:message key="admin.projectedit.name" />:</label>
            <input type="text" name="project_name" required pattern=".+" value="${project.name}" />
        </li>

        <li>
            <label for="deadline"><fmt:message key="admin.projectedit.deadline" />:</label>
            <input type="date" name="project_deadline" min="${project.deadline}" value="${project.deadline}" required/>
        </li>
        <br>
        <button class="submit" type="submit" >
            <fmt:message key="admin.projectedit.button.edit" />
        </button>

    </form>

</div>

<jsp:include page="../jspParts/footer.jsp"/>