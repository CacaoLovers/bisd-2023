<%--
  Created by IntelliJ IDEA.
  User: marsf
  Date: 17.10.2022
  Time: 14:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <style><%@include file="/css/edit.css" %></style>
    <c:choose>
        <c:when test="${requestScope.entity.getClass().getName().equals(\"models.Table\")}"><title>Столик</title></c:when>
        <c:when test="${requestScope.entity.getClass().getName().equals(\"models.Booking\")}"><title>Бронь</title></c:when>
        <c:when test="${requestScope.entity.getClass().getName().equals(\"models.Guest\")}"><title>Клиент</title></c:when>
    </c:choose>
</head>
<body>
<main>
    <c:choose>

        <c:when test="${requestScope.entity.getClass().getName().equals(\"models.Table\")}">

            <div class = 'cart-title'>
                Редактор столика #${requestScope.entity.getCapacity()}
            </div>

            <form class = 'cart-info' action="edit" method="post">
                <div class = 'cart-text'><span style="float:left;"> Место: </span> <span style = "float:right;">
                            <select name = "place">
                                <option value="lounge" <c:if test="${requestScope.entity.getPlacement().equals(\"lounge\")}"> selected </c:if>> Lounge </option>
                                <option value="VIP-zone" <c:if test="${requestScope.entity.getPlacement().equals(\"VIP-zone\")}"> selected </c:if>> VIP-zone </option>
                                <option value="veranda" <c:if test="${requestScope.entity.getPlacement().equals(\"veranda\")}"> selected </c:if>> Veranda </option>
                            </select>
                        </span></div>
                <div class = 'cart-text'><span style="float:left;"> Вместительность: </span> <span style = "float:right;"><input type="text" name = "capacity" value="${requestScope.entity.getCapacity()}"></span></div>
                <div class = 'cart-text'><span style="float:left;"> Описание: </span> <span style = "float:right;"><input type="text" name="comment" value="${requestScope.entity.getDescription()}"></span></div>
                <input type="hidden" name="id" value="${requestScope.entity.getNumber()}">
                <button type="submit" name="entity" value="table">Изменить</button>
            </form>

        </c:when>

        <c:when test="${requestScope.entity.getClass().getName().equals(\"models.Guest\")}">

            <div class = 'cart-title'>
                Редактор клиента
            </div>

            <form class = 'cart-info' action="edit" method="post">
                <div class = 'cart-text'><span style="float:left;"> Имя: </span> <span style = "float:right;"><input type="text" name = "first_name" value="${requestScope.entity.getFirstName()}"></span></div>
                <div class = 'cart-text'><span style="float:left;"> Фамилия: </span> <span style = "float:right;"><input type="text" name = "last_name" value="${requestScope.entity.getLastName()}"></span></div>
                <div class = 'cart-text'><span style="float:left;"> Телефон: </span> <span style = "float:right;"><input type="text" name = "phone_number" value="${requestScope.entity.getPhoneNumber()}"></span></div>
                <div class = 'cart-text'><span style="float:left;"> Комментарий: </span> <span style = "float:right;"><input type="text" name="comment_guest" value="${requestScope.entity.getComment()}"></span></div>
                <input type="hidden" name="id" value="${requestScope.entity.getId()}">
                <button type="submit" name="entity" value="client">Изменить</button>
            </form>

        </c:when>

        <c:when test="${requestScope.entity.getClass().getName().equals(\"models.Booking\")}">

            <div class = 'cart-title'>
                Редактор брони
            </div>

            <form class = 'cart-info' action="edit" method="post">
                <div class = 'cart-text'><span style="float:left;"> Номер столика: </span> <span style = "float:right;"><input type="text" name = "id_table" value="${requestScope.entity.getTable()}"></span></div>
                <div class = 'cart-text'><span style="float:left;"> Номер клиента: </span> <span style = "float:right;"><input type="text" name = "phone_number" value="${requestScope.entity.getPhoneNumber()}"></span></div>
                <div class = 'cart-text'><span style="float:left;"> Дата: </span> <span style = "float:right;"><input type="text" name = "date" value="${requestScope.entity.getDate()}"></span></div>

                <button type="submit" name="entity" value="booking">Изменить</button>
            </form>

        </c:when>


    </c:choose>
</main>
</body>
</html>
