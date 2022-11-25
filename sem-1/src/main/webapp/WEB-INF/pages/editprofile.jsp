
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Редактировние профиля</title>
    <link href="/css/editprofile.css" rel="stylesheet" type="text/css">
    <link href="/css/head.css" rel="stylesheet" type="text/css">
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

        <form class="edit-update" method="post" action="/profile/edit">
            <div class = "info-edit-entity">Основная информация</div>
            <div class = "edit-entity">
                Ваше имя <input type = "text" name="first_name" value="${requestScope.entity.getFirstName()}" placeholder="Введите имя">
                Ваша фамилия <input type = "text" name="last_name" value="${requestScope.entity.getLastName()}" placeholder="Введите фамилию">
            </div>
            <div class = "edit-entity">
                Город <select id="city-select" name = "city">
                    <c:forEach var="city" items="${cities}">
                        <option value="${city}" <c:if test="${requestScope.entity.getCity() != null && requestScope.entity.getCity().equals(city)}">selected</c:if>>${city}</option>
                    </c:forEach>
                </select>
                Район <select id="district-select">
                    <c:forEach items="${requestScope.districtSet.get(requestScope.entity.getCity().toString())}" var="district">
                        <option value="${district}">${district}</option>
                    </c:forEach>
                </select>
            </div>
            <div class = "info-edit-entity">Контактная информация</div>
            <div class = "edit-entity">
                Телефон <input type="text" name="phone_number" value="${requestScope.entity.getPhoneNumber()}" placeholder="Введите номер телефона">
                Почта <input type="text" name="mail" value="${requestScope.entity.getMail()}" placeholder="Введите почту">
            </div>
            <div class = "edit-entity">
                <input type="hidden" name="login" value="${requestScope.entity.getLogin()}">
                <input type = "submit" value="Сохранить" class = "save-block">
            </div>
        </form>
    </main>
</body>
<script src="/js/jquery-3.6.1.min.js"></script>
<script>
    document.getElementById("city-select").addEventListener("change", function () {
        let currentList = document.getElementById("district-select")
        let selectList = document.createElement("select");
        selectList.setAttribute("id", "district-select");
        selectList.setAttribute("name", "district");
        let city;
        let district;
        let options;
        <c:forEach items="${districtSet.keySet()}" var="city">
            city = '${city}';
            if (this.value == city){
                <c:forEach items="${districtSet.get(city)}" var="district">
                    district = '${district}';
                    options = document.createElement("option");
                    options.value = district;
                    options.text = district;
                    selectList.appendChild(options);
                </c:forEach>
            }
        </c:forEach>
        currentList.parentNode.replaceChild(selectList, currentList);
    })
</script>
</html>
