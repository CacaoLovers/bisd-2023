<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Вход</title>
    <link href="/css/login.css" rel="stylesheet" type="text/css">
</head>
<body>

    <main>
        <div>
            <c:if test="${requestScope.message != null}">
                <div class="error-block">
                    <p>${requestScope.message}</p>
                </div>
            </c:if>
            <form class = "login-form" action="login" method="post">
                <p>Введите логин</p>
                <input type = "text" placeholder="Логин" name = "login">
                <p>Введите пароль</p>
                <input type = "password" placeholder="Пароль" name = "password">
                <button type="submit">Войти</button>
            </form>
            <div class = "signup-block">
                <p class = "signup-text">Регистрация</p>
                <svg class="waves" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"
                     viewBox="0 24 150 28" preserveAspectRatio="none" shape-rendering="auto">
                    <defs>
                        <path id="gentle-wave" d="M-160 44c30 0 58-18 88-18s 58 18 88 18 58-18 88-18 58 18 88 18 v44h-352z" />
                    </defs>
                    <g class="parallax">
                        <use xlink:href="#gentle-wave" x="48" y="0" fill="rgba(255,255,255,0.7" />
                        <use xlink:href="#gentle-wave" x="48" y="3" fill="rgba(255,255,255,0.5)" />
                        <use xlink:href="#gentle-wave" x="48" y="5" fill="rgba(255,255,255,0.3)" />
                        <use xlink:href="#gentle-wave" x="48" y="7" fill="#fff" />
                    </g>
                </svg>
                <div class = "signup-block-form">

                    <form class = "signup-form" action="signup" method="post">

                        <p>Введите логин</p>
                        <input type = "text" placeholder="Логин" name = "login">
                        <p>Введите почту</p>
                        <input type = "text" placeholder="Почта" name = "mail">
                        <p>Введите пароль</p>
                        <input type = "password" placeholder="Пароль" name = "password">
                        <button type="submit">Регистрация</button>

                    </form>
                </div>
            </div>
        </div>

        <div class="fix-block"></div>
    </main>
</body>
<script src="/js/jquery-3.6.1.min.js"></script>
<script src="/js/login.js"></script>
<script>
    <c:if test="${requestScope.message != null}">
    $(document).ready(function () {
        $('.error-block').animate({top: "20px"}, 800, "linear", function (){$('.error-block').fadeOut(4000)})
    })
    </c:if>
</script>
</html>
