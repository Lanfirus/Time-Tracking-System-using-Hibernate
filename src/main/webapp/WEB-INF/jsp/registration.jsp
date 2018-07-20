<%@ include file="jspParts/general.jsp" %>
<%@ include file="jspParts/header-empty.jsp"%>

<div class="main">
<form class="contact_form" action="registration" method="post" name="contact_form">

    <ul>
        <li>
                 <h2><fmt:message key="registration.formName" /></h2>
                 <span class="required_notification"><fmt:message key="registration.mandatoryFields" /></span>
                 <br>
                 <c:if test = "${not empty requestScope.not_unique_login}">
                     <h5 style="color:red;"><fmt:message key="registration.message.notUniqueLogin" /></h5>
                 </c:if>
                 <br>
                <c:if test = "${not empty requestScope.bad_registration_data}">
                    <h5 style="color:red;"><fmt:message key="registration.message.incorrectData" /></h5>
                </c:if>
        </li>

        <li>
            <label for="login"><fmt:message key="registration.login" />:</label>
            <input type="text" name="employee_login" placeholder="<fmt:message key="registration.loginPlaceholder" />" required
                pattern="^(?!
                    <c:if test = "${not empty requestScope.loginProblem}">
                        ${requestScope.employee_login}
                    </c:if>
                    .+"
                    value="${requestScope.employee_login}"
            />
            <span class="form_hint">
                <c:if test = "${not empty requestScope.loginProblem}">
                    <fmt:message key="registration.loginProblemRegexp" />
                </c:if>
                <fmt:message key="registration.loginRegexp" /></span>
        </li>

        <li>
            <label for="password"><fmt:message key="registration.password" />:</label>
            <input type="password" name="employee_password" placeholder="<fmt:message key="registration.passwordPlaceholder" />"
                required pattern="<fmt:message key="registration.pattern.employee_password" />"
            />
            <span class="form_hint"><fmt:message key="registration.passwordRegexp" /></span>
        </li>

        <li>
            <label for="name"><fmt:message key="registration.name" />:</label>
            <input type="text" name="employee_name" placeholder="<fmt:message key="registration.namePlaceholder" />"
                required pattern="<fmt:message key="registration.pattern.employee_name" />"
                value="${requestScope.employee_name}"
            />
            <span class="form_hint"><fmt:message key="registration.nameRegexp" /></span>
        </li>

        <li>
            <label for="surname"><fmt:message key="registration.surname" />:</label>
            <input type="text" name="employee_surname" placeholder="<fmt:message key="registration.surnamePlaceholder" />"
                required pattern="<fmt:message key="registration.pattern.employee_surname" />"
                value="${requestScope.employee_surname}"

            />
            <span class="form_hint"><fmt:message key="registration.surnameRegexp" /></span>
        </li>

        <li>
            <label for="patronymic"><fmt:message key="registration.patronymic" />:</label>
            <input type="text" name="employee_patronymic" placeholder="<fmt:message key="registration.patronymicPlaceholder" />"
                pattern="<fmt:message key="registration.pattern.employee_patronymic" />"
                <c:if test = "${not empty requestScope.employee_patronymic}">
                        value="${requestScope.employee_patronymic}"
                </c:if>
            />
            <span class="form_hint"><fmt:message key="registration.patronymicRegexp" /></span>
        </li>

        <li>
            <label for="email"><fmt:message key="registration.email" />:</label>
            <input type="text" name="employee_email" placeholder="email@email.com" required
                pattern="<fmt:message key="registration.pattern.employee_email" />"
                value="${requestScope.employee_email}"
            />
            <span class="form_hint"><fmt:message key="registration.emailRegexp" /></span>
        </li>

        <li>
            <label for="mobilephonenumber"><fmt:message key="registration.mobilePhoneNumber" />:</label>
            <input type="text" name="employee_mobile_phone" placeholder="380501234567"
                required pattern="<fmt:message key="registration.pattern.employee_mobile_phone" />"
                value="${requestScope.employee_mobile_phone}"
            />
            <span class="form_hint"><fmt:message key="registration.mobilePhoneNumberRegexp" /></span>
        </li>

        <li>
            <label for="comment"><fmt:message key="registration.comment" />:</label>
            <input type="text" name="employee_comment" placeholder="<fmt:message key="registration.commentPlaceholder" />"
                pattern="<fmt:message key="registration.pattern.employee_comment" />"
                <c:if test = "${not empty requestScope.employee_comment}">
                        value="${requestScope.employee_comment}"
                </c:if>
            />
            <span class="form_hint"><fmt:message key="registration.commentRegexp" /></span>
        </li>

        <li>

        <button class="submit" type="submit"><fmt:message key="registration.submit" /></button>

        </li>

    </ul>

</form>
       </div>

<jsp:include page="jspParts/footer.jsp"/>