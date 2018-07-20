<%@ include file="../jspParts/general.jsp" %>
<%@ include file="../jspParts/header-employee.jsp"%>

<div class="main">
    <h2><fmt:message key="employee.myprojects.title" /></h2>

    <br>

    <form method="post" action="project_sort" name="project_sort_form">
        <p><fmt:message key="employee.myprojects.sort.fieldName" />
        <select name="sort_field">
            <option value="project_id" selected >
                <fmt:message key="employee.myprojects.id" />
            </option>

            <option value="project_name" >
                <fmt:message key="employee.myprojects.name" />
            </option>

            <option value="project_deadline" >
                <fmt:message key="employee.myprojects.deadline" />
            </option>

            <option value="project_status" >
                <fmt:message key="employee.myprojects.status" />
            </option>
        </select>
        <p>

        <p><fmt:message key="employee.myprojects.sort.fieldName" />
            <select name="sort_order">
            <option value="asc" selected >
                <fmt:message key="employee.myprojects.sort.asc" />
            </option>

            <option value="dsc" >
                <fmt:message key="employee.myprojects.sort.dsc" />
            </option>
        </select>
        <p>
        <button class="submit" type="submit" >
            <fmt:message key="employee.myprojects.sort.button.sort" />
        </button>

    </form>

    <br>

    <nav>
        <ul class="pagination" >

            <c:forEach begin="1" end="${number_of_pages}">

                <c:set var="page_counter" value="${page_counter + 1}" scope="page"/>
                <form action="projects" method="post">
                    <input type="hidden" value="${page_counter}" name="current_page">
                    <input style="margin: 2px" type="submit" class="page-link" value="${page_counter}">
                </form>

            </c:forEach>
        </ul>
    </nav>

    <table>
        <tr>
            <th><fmt:message key="employee.myprojects.id"/></th>
            <th><fmt:message key="employee.myprojects.name"/></th>
            <th><fmt:message key="employee.myprojects.deadline"/></th>
            <th><fmt:message key="employee.myprojects.status"/></th>
        </tr>

        <c:forEach var="project" items="${myProjects}">
            <tr>
                <td><c:out value="${project.projectId}"/></td>
                <td><c:out value="${project.projectName}"/></td>
                <td><fmt:parseDate value="${project.projectDeadline}" pattern="yyyy-MM-dd" var="parsedDate" type="date" />
                <fmt:formatDate value="${parsedDate}" type="date" dateStyle = "short"/></td>
                <td>
                      <c:if test = "${project.projectStatus == 'NEW'}">
                         <fmt:message key="project.status.new" />
                      </c:if>

                      <c:if test = "${project.projectStatus == 'ASSIGNED'}">
                         <fmt:message key="project.status.assigned" />
                      </c:if>

                      <c:if test = "${project.projectStatus == 'FINISHED'}">
                         <fmt:message key="project.status.finished" />
                      </c:if>

                      <c:if test = "${project.projectStatus == 'CANCELLED'}">
                         <fmt:message key="project.status.cancelled" />
                      </c:if>
                </td>
            </tr>
        </c:forEach>

    </table>

</div>

<jsp:include page="../jspParts/footer.jsp"/>