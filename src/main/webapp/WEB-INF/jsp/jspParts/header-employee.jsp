<!DOCTYPE html>

<head>
    <meta charset="utf-8">
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
                            <a class="main-menu-link" href="${pageContext.request.contextPath}/tts/task_show">
                                <fmt:message key="header.tasks" />
                            </a>
                            <ul class="sub-menu">

                                <li class="sub-menu-li">
                                    <a class="sub-menu-link" href="${pageContext.request.contextPath}/tts/new_task_form">
                                        <fmt:message key="header.task.request" />
                                    </a>
                                </li>

                            </ul>
                        </li>

                        <li class="main-menu-item ">
                            <a class="main-menu-link" href="${pageContext.request.contextPath}/tts/projects">
                                <fmt:message key="header.projects" />
                            </a>
                        </li>

                        <li class="main-menu-item ">
                            <a class="main-menu-link" href="${pageContext.request.contextPath}/tts/profile">
                                <fmt:message key="header.profile" />
                            </a>
                        </li>

                        <li class="main-menu-item ">
                            <a class="main-menu-link" href="${pageContext.request.contextPath}/tts/contacts">
                                <fmt:message key="header.contacts" />
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
