<%@ include file="../jspParts/general.jsp" %>
<%@ include file="../jspParts/header-admin.jsp"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

<div class="main">
    <h2><fmt:message key="admin.archive.projects.title" /></h2>

            <display:table name="myProjects" id="project"
                           sort="list" requestURI = "" pagesize="5">
                <display:column property="id" titleKey="admin.allprojects.id"
                                sortable="true" headerClass="sortable" />
                <display:column property="name" titleKey="admin.allprojects.name"
                                sortable="true" headerClass="sortable" />
                <display:column titleKey="admin.allprojects.status" sortable="true" headerClass="sortable" >
                      <c:if test = "${project.status == 'NEW'}">
                         <fmt:message key="project.status.new" />
                      </c:if>

                      <c:if test = "${project.status == 'ASSIGNED'}">
                         <fmt:message key="project.status.assigned" />
                      </c:if>

                      <c:if test = "${project.status == 'FINISHED'}">
                         <fmt:message key="project.status.finished" />
                      </c:if>

                      <c:if test = "${project.status == 'CANCELLED'}">
                         <fmt:message key="project.status.cancelled" />
                      </c:if>
                </display:column>
                <display:column titleKey="admin.allprojects.deadline" sortable="true" headerClass="sortable" >
                    <fmt:parseDate value="${project.deadline}" pattern="yyyy-MM-dd" var="parsedDate" type="date" />
                    <fmt:formatDate value="${parsedDate}" type="date" dateStyle = "short"/>
                </display:column>
            </display:table>

</div>

<jsp:include page="../jspParts/footer.jsp"/>