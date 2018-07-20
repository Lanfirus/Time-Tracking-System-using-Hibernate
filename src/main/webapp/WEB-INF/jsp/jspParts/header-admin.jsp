<!DOCTYPE html>

<head>

    <title><fmt:message key="header.title" /></title>

    <link rel="stylesheet" type="text/css" href="http://localhost:8888/company/resources/css/bootstrap.css" >
    <link rel="stylesheet" type="text/css" href="http://localhost:8888/company/resources/css/base.css" >
    <link rel="stylesheet" type="text/css" href="http://localhost:8888/company/resources/css/header.css" >
    <link rel="stylesheet" type="text/css" href="http://localhost:8888/company/resources/css/footer.css" >
    <link rel="stylesheet" type="text/css" href="http://localhost:8888/company/resources/css/reg-form.css" >

</head>

<body>

    <header class="header">
        <div class="header-container">
            <a href="/company/tts" class="header-logo"></a>

            <nav class="main-menu header-item">
                <ul class="main-menu-ul">

                        <li class="main-menu-item  main-menu-item-parent ">
                                                    <a class="main-menu-link" href="${pageContext.request.contextPath}/tts/admin/task">
                                <fmt:message key="header.task.show.all" />
                            </a>
                            <ul class="sub-menu">

                                <li class="sub-menu-li">
                                    <a class="sub-menu-link" href="${pageContext.request.contextPath}/tts/admin/task_show_assigned">
                                        <fmt:message key="header.task.show.assigned" />
                                    </a>
                                </li>

                                <li class="sub-menu-li">
                                    <a class="sub-menu-link" href="${pageContext.request.contextPath}/tts/admin/task_show_finished">
                                        <fmt:message key="header.task.show.finished" />
                                    </a>
                                </li>

                                <li class="sub-menu-li">
                                    <a class="sub-menu-link" href="${pageContext.request.contextPath}/tts/admin/task_show_cancelled">
                                        <fmt:message key="header.task.show.cancelled" />
                                    </a>
                                </li>

                                <li class="sub-menu-li">
                                    <a class="sub-menu-link" href="${pageContext.request.contextPath}/tts/admin/task_show_new_requests">
                                        <fmt:message key="header.task.show.newRequest" />
                                    </a>
                                </li>

                                <li class="sub-menu-li">
                                    <a class="sub-menu-link" href="${pageContext.request.contextPath}/tts/admin/task_show_not_approved">
                                        <fmt:message key="header.task.show.notApproved" />
                                    </a>
                                </li>


                            </ul>
                        </li>

                        <li class="main-menu-item  main-menu-item-parent ">
                            <a class="main-menu-link" href="${pageContext.request.contextPath}/tts/admin/project">
                                <fmt:message key="header.project.show.all" />
                            </a>
                            <ul class="sub-menu">

                                <li class="sub-menu-li">
                                    <a class="sub-menu-link" href="${pageContext.request.contextPath}/tts/admin/project_show_assigned">
                                        <fmt:message key="header.project.show.assigned" />
                                    </a>
                                </li>

                                <li class="sub-menu-li">
                                    <a class="sub-menu-link" href="${pageContext.request.contextPath}/tts/admin/project_show_cancelled">
                                        <fmt:message key="header.project.show.cancelled" />
                                    </a>
                                </li>

                                <li class="sub-menu-li">
                                    <a class="sub-menu-link" href="${pageContext.request.contextPath}/tts/admin/project_show_finished">
                                        <fmt:message key="header.project.show.finished" />
                                    </a>
                                </li>


                                <li class="sub-menu-li">
                                    <a class="sub-menu-link" href="${pageContext.request.contextPath}/tts/admin/project_show_new">
                                        <fmt:message key="header.project.show.new" />
                                    </a>
                                </li>


                            </ul>
                        </li>

                        <li class="main-menu-item ">
                            <a class="main-menu-link" href="${pageContext.request.contextPath}/tts/admin/employees">
                                <fmt:message key="header.employee.show.all" />
                            </a>
                        </li>

                        <li class="main-menu-item  main-menu-item-parent ">
                            <a class="main-menu-link" href="${pageContext.request.contextPath}/tts/admin/archive_projects">
                                <fmt:message key="header.archive.show.all" />
                            </a>
                            <ul class="sub-menu">

                                <li class="sub-menu-li">
                                    <a class="sub-menu-link" href="${pageContext.request.contextPath}/tts/admin/archive_tasks">
                                        <fmt:message key="header.archive.show.tasks" />
                                    </a>
                                </li>

                            </ul>
                        </li>

                        <li class="main-menu-item ">
                            <a class="main-menu-link" href="${pageContext.request.contextPath}/tts/profile">
                                <fmt:message key="header.profile" />
                            </a>
                        </li>

                </ul>
            </nav>

            <a href="${pageContext.request.contextPath}/tts/logout" class="button button-header"><fmt:message key="header.logout" /></a>
        </div>

        <div style="left:10px; top:20px; position:relative;">
                    <form action="${pageContext.request.contextPath}/app/language" method="post">
                        <select name="language" >
                            <option value="en_US" ${language == 'en_US' ? 'selected' : ''}>
                                    <fmt:message key="header.language.english" />
                            </option>
                            <option value="uk_UA" ${language == 'uk_UA' ? 'selected' : ''}>
                                <fmt:message key="header.language.ukrainian" />
                            </option>
                        </select>
                        <button class="button-select" style="padding:0 10px" type="submit"><fmt:message key="header.language.select" /></button>
                    </form>
        </div>
    </header>
