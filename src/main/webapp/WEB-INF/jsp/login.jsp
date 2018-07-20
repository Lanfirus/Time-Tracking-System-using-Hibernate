<%@ include file="jspParts/general.jsp" %>
<%@ include file="jspParts/header-empty.jsp"%>

<div class="main">
<form class="contact_form" action="login" method="post" name="contact_form">

    <ul>
        <li>
            <h2><fmt:message key="login.formName" /></h2>
        </li>

        <li>
            <label for="login"><fmt:message key="login.login" />:</label>
            <input type="text" name="login" placeholder="<fmt:message key="login.loginPlaceholder" />" required pattern=".+"/>
            <span class="form_hint"><fmt:message key="login.loginRegexp" /></span>
        </li>

        <li>
            <label for="password"><fmt:message key="login.password" />:</label>
            <input type="password" name="password" placeholder="<fmt:message key="login.passwordPlaceholder" />" required pattern=".+"/>
            <span class="form_hint"><fmt:message key="login.passwordRegexp" /></span>
        </li>


        <li>
            <button class="submit" type="submit"><fmt:message key="login.submit" /></button>
        </li>

    </ul>

</form>
</div>
<jsp:include page="jspParts/footer.jsp"/>
