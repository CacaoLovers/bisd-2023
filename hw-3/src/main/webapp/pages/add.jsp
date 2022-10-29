<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: marsf
  Date: 16.10.2022
  Time: 18:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Добавление</title>
    <style><%@include file="/css/add.css" %></style>

</head>
<body>
    <main>
        <c:choose>

            <c:when test="${requestScope.entity.equals(\"table\")}">

                <div class = 'cart-title'>
                    Добавление столика
                </div>

                <form class = 'cart-info' action="add" method="post">
                    <div class = 'cart-text'><span style="float:left;"> Номер столика: </span> <span style = "float:right;"><input type="text" name = "id"></span></div>
                    <div class = 'cart-text'><span style="float:left;"> Место: </span> <span style = "float:right;">
                        <select name = "place">
                            <option value="lounge"> Lounge </option>
                            <option value="VIP-zone"> VIP-zone </option>
                            <option value="veranda"> Veranda </option>
                        </select>
                    </span></div>
                    <div class = 'cart-text'><span style="float:left;"> Вместительность: </span> <span style = "float:right;"><input type="text" name = "capacity"></span></div>
                    <div class = 'cart-text'><span style="float:left;"> Описание: </span> <span style = "float:right;"><input type="text" name="comment"></span></div>
                    <button type="submit" name="entity" value="table">Добавить</button>
                </form>

            </c:when>

            <c:when test="${requestScope.entity.equals(\"guest\")}">

                <div class = 'cart-title'>
                    Добавление клиента
                </div>

                <form class = 'cart-info' action="add" method="post">
                    <div class = 'cart-text'><span style="float:left;"> Имя: </span> <span style = "float:right;"><input type="text" name = "first_name"></span></div>
                    <div class = 'cart-text'><span style="float:left;"> Фамилия: </span> <span style = "float:right;"><input type="text" name = "last_name"></span></div>
                    <c:if test="${requestScope.bookingAddNumber != null}">
                        <input type="hidden" name="update" value="${requestScope.bookingAddNumber}">
                    </c:if>
                    <div class = 'cart-text'><span style="float:left;"> Телефон: </span> <span style = "float:right;"><input type="text" name = "phone_number" value="${requestScope.bookingAddNumber}"></span></div>
                    <div class = 'cart-text'><span style="float:left;"> Комментарий: </span> <span style = "float:right;"><input type="text" name="comment_guest"></span></div>
                    <button type="submit" name="entity" value="guest">Добавить</button>
                </form>

            </c:when>


        </c:choose>
    </main>
</body>
</html>
