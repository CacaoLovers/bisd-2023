<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Пользователи</title>
    <link href="../css/admin-missing.css" rel="stylesheet" type="text/css">
    <link href="../css/head.css" rel="stylesheet" type="text/css">

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
        <table class="user-table">
            <tr class="user-table-column">
                <td>ID</td>
                <td>Логин</td>
                <td>Почта</td>
                <td>Телефон</td>
                <td>Имя</td>
                <td>Фамилия</td>
                <td>Город</td>
                <td style="background: none"></td>
            </tr>
        <c:forEach items="${userSet}" var="user">
            <tr>
                <td>${user.getId()}</td>
                <td>${user.getLogin()}</td>
                <td>${user.getMail()}</td>
                <td>${user.getPhoneNumber()}</td>
                <td>${user.getFirstName()}</td>
                <td>${user.getLastName()}</td>
                <td>${user.getCity()}</td>
                <td>
                    <form action="/admin/user" method="post">
                        <input type="hidden" name="user_id" value="${user.getId()}">
                        <button type="submit" name="action" value="remove">Удалить</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </table>
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
