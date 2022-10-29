<%--
  Created by IntelliJ IDEA.
  User: marsf
  Date: 13.10.2022
  Time: 18:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <style><%@include file="/css/cart.css" %></style>
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

                    <div class = 'cart-title'><c:out value="Столик #${requestScope.entity.getNumber()}"/></div>
                    <div class = 'cart-info'>
                        <div class = 'cart-text'><span style="float:left;"> Вместимость: </span> <span style = "float:right;"><c:out value="${requestScope.entity.getCapacity()}"/></span></div>
                        <div class = 'cart-text'><span style="float:left;"> Место: </span> <span style = "float:right;"><c:out value="${requestScope.entity.getPlacement()}"/></span></div>
                        <div class = 'cart-text'><span style="float:left;"> Описание: </span> <span style = "float:right;"><c:out value="${requestScope.entity.getDescription()}"/></span></div>
                        <div class = 'cart-text'> Бронь: </div>
                        <c:forEach var="booking" items="${bookingListByTable}">

                            <div class = 'cart-table-booking'>Номер ${booking.getPhoneNumber()} на ${requestScope.dateFormat.format(booking.getDate())} в течение ${booking.getDuration()} часов</div>

                        </c:forEach>
                    </div>
                    <div class="cart-form">
                        <form action="/edit" method="get">
                            <input type="hidden" name="id" value="${requestScope.entity.getNumber()}">
                            <button type="submit" name="entity" value="table">Редактировать</button>
                        </form>
                        <form action="/remove" method="post">
                            <input type="hidden" name="id" value="${requestScope.entity.getNumber()}">
                            <button type="submit" name="entity" value="table">Удалить</button>
                        </form>
                    </div>


                </c:when>

                <c:when test="${requestScope.entity.getClass().getName().equals(\"models.Guest\")}">

                    <div class = 'cart-title'><c:out value="${requestScope.entity.getFirstName()} "/>
                        <c:out value="${requestScope.entity.getLastName()}"/>
                    </div>

                    <div class = 'cart-info'>
                        <div class = 'cart-text'><span style="float:left;"> Телефон: </span> <span style = "float:right;"><c:out value="${requestScope.entity.getPhoneNumber()}"/></span></div>
                        <div class = 'cart-text'><span style="float:left;"> ID: </span> <span style = "float:right;"><c:out value="${requestScope.entity.getId()}"/></span></div>
                        <div class = 'cart-text'><span style="float:left;"> Комментарий: </span> <span style = "float:right;"><c:out value="${requestScope.entity.getComment()}"/></span></div>
                    </div>

                    <div class="cart-form">
                        <form action="/edit" method="get">
                            <input type="hidden" name="id" value="${requestScope.entity.getId()}">
                            <button type="submit" name="entity" value="client">Редактировать</button>
                        </form>
                        <form action="/remove" method ="post">
                            <input type="hidden" name="id" value="${requestScope.entity.getId()}">
                            <button type="submit" name="entity" value = "client">Удалить</button>
                        </form>
                    </div>

                </c:when>


                <c:when test="${requestScope.entity.getClass().getName().equals(\"models.Booking\")}">

                    <div class = 'cart-title'>Бронь</div>

                </c:when>
            </c:choose>
        </main>
    </body>
</html>
