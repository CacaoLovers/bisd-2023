<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Админ-панель</title>
    <link href="../css/head.css" rel="stylesheet" type="text/css">
    <link href="../css/admin-missing.css" rel="stylesheet" type="text/css">

</head>
<body>
    <c:if test="${requestScope.message != null}">
        <div class="error-block">
            <p>${requestScope.errorMessage}</p>
        </div>
    </c:if>
    <header>
        <span class = "logo"><img src="../../images/logo.jpg"><a href="/">PETHOME</a></span>
        <span class = header-menu>
                <a class = "header-menu-entity" href="/map?action=found">Потерял</a>
                <a class = "header-menu-entity" href="/map?action=lost">Нашел</a>
                <a class = "header-menu-entity" href="/volunteer">
                        <c:if test="${sessionScope.volunteer == null}">Стать волонтером</c:if>
                        <c:if test="${sessionScope.volunteer != null}">Волонтерство</c:if>
                    </a>
            </span>
        <c:choose >
            <c:when test="${sessionScope.status.equals(\"guest\")}">
                <form class="login-button-form" action="login" method="get">
                    <button type="submit" class="login-button">Войти</button>
                </form>
            </c:when>
            <c:when test="${sessionScope.status.equals(\"login\")}">
                <form class="login-button-form" action="profile" method="get">
                    <input type ="hidden" name="login" value="${sessionScope.login}">
                    <button type = "submit" class="login-button">${sessionScope.login}</button>
                </form>
            </c:when>
        </c:choose>
    </header>
    <main>
        <div class="admin-menu-container">
            <a class="admin-menu-button" href="/admin/missing">Объявления</a>
            <a class="admin-menu-button" href="/admin/user">Пользователи</a>
        </div>
    </main>
</body>

</html>
