<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Профиль</title>
    <link href="/css/profile.css" rel="stylesheet" type="text/css">
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
        <div class="left-container">
            <div class = "profile-photo" style="background-image: url('/images/cat_1.jpg')"></div>
            <c:if test="${requestScope.loginEnter.equals(sessionScope.login)}">
                <form class = "form-left" action="profile/edit" method="get">
                    <button class="form-left-button" type="submit" name="login" value="${sessionScope.login}">Изменить анкету</button>
                </form>
                <c:if test="${sessionScope.role.equals(\"admin\")}">
                    <form class = "form-left" action="admin" method="get">
                        <button class="form-left-button" type="submit" name="login" value="${sessionScope.login}">Админ панель</button>
                    </form>
                </c:if>
                <form class = "form-left" action="profile" method="post">
                    <button class="form-left-button" type="submit" name="action" value="exit">Выйти</button>
                </form>
            </c:if>
        </div>

        <div class="right-container">

            <div class = "info-block">

            <c:if test="${requestScope.entity.getFirstName() == null}">
                <p class = "text-info-header"><c:out value="${requestScope.entity.getLogin()}"/></p>
            </c:if>
            <c:if test="${requestScope.entity.getFirstName() != null}">
                <p class = "text-info-header"><c:out value="${requestScope.entity.getFirstName()}  ${requestScope.entity.getLastName()}"/></p>
            </c:if>
            <p class = "text-info"><c:out value="Город: ${requestScope.entity.getCity()}"/></p>
            <p class = "text-info"><c:out value="Почта: ${requestScope.entity.getMail()}"/></p>
            <p class = "text-info"><c:out value="Телефон: ${requestScope.entity.getPhoneNumber()}"/></p>

            </div>


            <div class = "block-menu">
                <div class="block-menu-header">
                    <div id="block-menu-header-notification" onclick="changeMenu(this)">Уведомления</div>
                    <div id="block-menu-header-missing" onclick="changeMenu(this)">Объявления</div>
                </div>
                <c:if test="${requestScope.loginEnter.equals(sessionScope.login)}">
                    <div class="block-menu-notification">
                        <c:if test="${requestScope.notificationSet.isEmpty()}">Уведомлений пока нет</c:if>
                        <c:forEach items="${notificationSet}" var="notification">
                            <div class="block-menu-notification-entity">
                                <p>Пользователь <a href="/profile?login=${notification.getIdFrom().getLogin()}">${notification.getIdFrom().getFirstName()} ${notification.getIdFrom().getLastName()}</a> нашел вашего питомца по <a href="/map?action=${notification.getMissing().getSide()}&missing_id=${notification.getMissing().getId()}"> объявлению </a></p>
                                <form action="/profile" method="post">
                                    <input type="hidden" name="missing_id" value="${notification.getMissing().getId()}">
                                    <button type="submit" name="action" value="confirm">Подтвердить</button>
                                </form>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
                <div class="block-menu-missing">
                    <c:forEach items="${missingSet}" var="missing">
                        <form class="block-menu-missing-form" action="/map" method="get">
                            <input type="hidden" name="action" value="${missing.getSide()}">
                            <button class="block-menu-missing-entity" name="missing_id" value="${missing.getId()}">
                                <div class="block-menu-missing-photo" style="background-image: url('/images/${missing.getPathImage()}')">
                                </div>
                                <div class="block-menu-missing-info">
                                    <p class = "missing-information-entity"><c:if test="${missing.getSide().equals('lost')}">Потеряли</c:if> <c:if test="${missing.getSide().equals('found')}">Ищем</c:if> ${missing.getName()}</p>
                                    <p class = "missing-information-entity" title= "${missing.getDescription()}">Приметы: ${missing.getDescription()}</p>
                                    <p class = "missing-information-entity" title="${missing.getPosition().getStreet()}">${missing.getPosition().getStreet()}</p>
                                    <p class = "missing-information-entity"><c:if test="${missing.getSide().equals('lost')}">Потерян</c:if> <c:if test="${missing.getSide().equals('found')}">Найден</c:if> ${missing.getDate()}</p>
                                </div>
                            </button>
                        </form>
                    </c:forEach>
                </div>
            </div>
        </div>

    </main>
</body>
<script src="/js/jquery-3.6.1.min.js"></script>
<script>
    $(document).ready(function () {
        $('#block-menu-header-notification').css('text-decoration', 'underline');
        <c:if test="${requestScope.loginEnter.equals(sessionScope.login)}">
            $('.block-menu-missing').hide();
        </c:if>
    });

    function changeMenu(e){
        if(e.id == 'block-menu-header-missing'){
            $(e).css('text-decoration', 'underline');
            $('#block-menu-header-notification').css('text-decoration', 'none');
            $('.block-menu-missing').show();
            $('.block-menu-notification').hide();
        }
        if(e.id == 'block-menu-header-notification'){
            $('#block-menu-header-missing').css('text-decoration', 'none');
            $(e).css('text-decoration', 'underline');
            $('.block-menu-missing').hide();
            $('.block-menu-notification').show();
        }
    }
</script>
<script>
    <c:if test="${requestScope.message != null}">
    $(document).ready(function () {
        $('.error-block').animate({top: "20px"}, 800, "linear", function (){$('.error-block').fadeOut(4000)})
    })
    </c:if>
</script>
</html>
