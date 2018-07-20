<%@ include file="jspParts/general.jsp" %>
<%@ include file="jspParts/header-empty.jsp"%>

<div class="main">
<h2>
    <fmt:message key="registration.successful" />
</h2>
<form>
    <button class="submit" type="submit" onclick='this.form.action="login_form";'>
        <fmt:message key="login.login" />
    </button>
</form>
</div>
<jsp:include page="jspParts/footer.jsp"/>