
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Волонтерство</title>
    <link href="/css/volunteer.css" rel="stylesheet" type="text/css">
    <link href="/css/head.css" rel="stylesheet" type="text/css">
</head>
<body>
    <c:if test="${requestScope.message != null}">
        <div class="error-block">
            <p>${requestScope.message}</p>
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
        <div class="district-block-container">
            <div class = "district-block-container-header">Текущие районы</div>
            <div class = "district-holder">
                <c:forEach items="${currentDistrict}" var="district">
                    <form action="/volunteer" method="post" class="district-form">
                        <input type="hidden" name="action" value="remove">
                        <input type="hidden" name = "district" value="${district}">
                        <button class="district" type="button">${district}
                            <input class ="district-remove" value="" type="submit">
                        </button>
                    </form>
                </c:forEach>
            </div>
        </div>
        <div class = "district-block-container">
            <div class = "district-block-container-header">Доступные районы</div>
            <div class = "district-holder">
                <c:forEach items="${districtSet}" var="district">
                    <form action="/volunteer" method="post" class="district-form">
                        <input type="hidden" name="action" value="add">
                        <button class="district" name = "district" value="${district}" type="submit">${district}</button>
                    </form>
                </c:forEach>
            </div>
        </div>
        <div class = "profile-volunteer-button-holder">
            <a class="profile-volunteer-button" href="/volunteer">Панель волонтера</a>
        </div>
    </main>
</body>
<script>
    <c:if test="${requestScope.message != null}">
    $(document).ready(function () {
        $('.error-block').animate({top: "20px"}, 800, "linear", function (){$('.error-block').fadeOut(4000)})
    })
    </c:if>
</script>
</html>
