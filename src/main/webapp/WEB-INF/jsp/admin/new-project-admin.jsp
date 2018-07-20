<%@ include file="../jspParts/general.jsp" %>
<%@ include file="../jspParts/header-admin.jsp"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib uri="http://localhost:8888/company/tags" prefix="dl"%>

<div class="main">
    <h2><fmt:message key="admin.newproject.title" /></h2>

    <dl:today/>

    <form method="post" action="new_project" name="project_creation_form">

        <li>
            <label for="name"><fmt:message key="admin.newproject.name" />:</label>
            <input type="text" name="project_name" required pattern=".+" />
        </li>

        <li>
            <label for="deadline"><fmt:message key="admin.newproject.deadline" />:</label>
            <input type="date" name="project_deadline" id="deadline" min="${today}" required value="${today}"/>
        </li>

    <br>
    <input type="hidden" name="project_status" value="new"/>
    <button class="submit" type="submit" >
        <fmt:message key="admin.newproject.button.create" />
    </button>

    </form>

</div>

<jsp:include page="../jspParts/footer.jsp"/>