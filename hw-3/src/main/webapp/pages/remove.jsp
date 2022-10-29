<%--
  Created by IntelliJ IDEA.
  User: marsf
  Date: 17.10.2022
  Time: 16:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Удаление</title>
    <style><%@include file="/css/remove.css" %></style>
</head>
<body>

    <header>
        <p>Удаление брони</p>
    </header>
    <main>
        <form class = "form-booking" method="post" action="remove">
            <c:forEach var="booking" items="${bookingSet}">

                <div class = "booking-entity">
                    <input type="checkbox" name="id" value="${booking.getTable()}_${booking.getGuest()}">
                    <c:out value="Столик #${booking.getTable()}    Клиент ${booking.getPhoneNumber()}     Дата ${booking.getDate()}"></c:out>
                </div>

            </c:forEach>
            <div style = "margin-top: 30px">
            <button class="form-button" type="submit" name="entity" value="booking">Удалить</button>
            </div>
        </form>
    </main>
</body>
</html>
