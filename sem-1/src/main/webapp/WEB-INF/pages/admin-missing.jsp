<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Объявления</title>
    <link href="../css/head.css" rel="stylesheet" type="text/css">
    <link href="../css/admin-missing.css" rel="stylesheet" type="text/css">

</head>
<body>
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
    <div class="missing-container">
        <c:forEach items="${missingSet}" var="missing">
            <form class="block-menu-missing-form" action="/map" method="get">
                <input type="hidden" name="action" value="${missing.getSide()}">
                <button class="block-menu-missing-entity" name="missing_id" value="${missing.getId()}">
                    <div class="block-menu-missing-photo" style="background-image: url('../images/${missing.getPathImage()}')">
                    </div>
                    <div class="block-menu-missing-info">
                        <p class = "missing-information-entity"><c:if test="${missing.getSide().equals('lost')}">Потерян</c:if> <c:if test="${missing.getSide().equals('found')}">Найден</c:if> ${missing.getName()}</p>
                        <p class = "missing-information-entity" title= "${missing.getDescription()}">Приметы: ${missing.getDescription()}</p>
                        <p class = "missing-information-entity" title="${missing.getStreet()}">${missing.getStreet()}</p>
                        <p class = "missing-information-entity"><c:if test="${missing.getSide().equals('lost')}">Потерян</c:if> <c:if test="${missing.getSide().equals('found')}">Найден</c:if> ${missing.getDate()}</p>
                    </div>
                </button>
            </form>
        </c:forEach>
    </div>
</main>
</body>
</html>
