<%@ include file="../jspParts/general.jsp" %>
<%@ include file="../jspParts/header-admin.jsp"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

<div class="main">
    <h2><fmt:message key="admin.archive.tasks.title" /></h2>

    <display:table name="myTasks" sort="list" id="task" requestURI = "" pagesize="5">
        <display:column property="id" titleKey="admin.alltasks.id"
            sortable="true" headerClass="sortable" />
        <display:column property="projectId" titleKey="admin.alltasks.projectId"
            sortable="true" headerClass="sortable" />
        <display:column property="employeeId" titleKey="admin.alltasks.employeeId"
            sortable="true" headerClass="sortable" />
        <display:column property="name" titleKey="admin.alltasks.name"
            sortable="true" headerClass="sortable" />
                <display:column titleKey="admin.alltasks.status"
                                sortable="true" headerClass="sortable" >
                    <c:if test = "${task.status == 'ASSIGNED'}">
                       <fmt:message key="task.status.assigned" />
                    </c:if>

                    <c:if test = "${task.status == 'FINISHED'}">
                       <fmt:message key="task.status.finished" />
                    </c:if>

                    <c:if test = "${task.status == 'CANCELLED'}">
                       <fmt:message key="task.status.cancelled" />
                    </c:if>
                </display:column>
        <display:column titleKey="admin.alltasks.deadline" sortable="true" headerClass="sortable" >
            <fmt:parseDate value="${task.deadline}" pattern="yyyy-MM-dd" var="parsedDate" type="date" />
            <fmt:formatDate value="${parsedDate}" type="date" dateStyle = "short"/>
        </display:column>
        <display:column property="spentTime" titleKey="admin.alltasks.spentTime"
            sortable="true" headerClass="sortable" />
                <display:column titleKey="admin.alltasks.approvalState"
                                sortable="true" headerClass="sortable" >
                     <c:if test = "${task.approvalState == 'APPROVED'}">
                        <fmt:message key="task.approvalState.approved" />
                     </c:if>

                     <c:if test = "${task.approvalState == 'NOT_APPROVED'}">
                        <fmt:message key="task.approvalState.notApproved" />
                     </c:if>

                     <c:if test = "${task.approvalState == 'NEW_REQUEST'}">
                        <fmt:message key="task.approvalState.newRequest" />
                     </c:if>
                </display:column>
    </display:table>

</div>

<jsp:include page="../jspParts/footer.jsp"/>