<%--
  Created by IntelliJ IDEA.
  User: marsf
  Date: 10.10.2022
  Time: 22:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Каталог</title>
    <style><%@include file="/css/catalog.css" %></style>
<%--    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>--%>

</head>
<body>

    <div class="pelena"></div>
    <div class = "filter-block">
        <p class="filter-text">Фильтр</p>
        <form class="filter-select" action="" method="post">
            <div class="filter-input"> Номер столика:
                <select name="table">
                    <option value="all">Все</option>
                    <c:forEach var="table" items="${tableSet}">
                        <option value="${table.getNumber()}">
                                <c:out value="Столик: ${table.getNumber()} "/>
                        </option>
                    </c:forEach>

                </select>
            </div>
            <div class="filter-input">Телефон:
                <select name="guest">
                    <option value="all">Все</option>
                    <c:forEach var="guest" items="${guestSet}">
                        <option value="${guest.getId()}">
                            <c:out value="${guest.getFirstName()} ${guest.getPhoneNumber()} "/>
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="filter-input">Дата: <input type="date" name = "date"></div>
            <button class="filter-press" type="submit" >Фильтр</button>
            <button type="button" class="filter-press filter-button">Назад</button>
        </form>
    </div>

    <main>
        <div class = "main-menu">
            <p class = "text-booking">Бронирование</p>
            <a href="/remove" class = "booking-filter">Удаление</a>
            <div class = "booking-filter filter-button">Фильтр</div>
        </div>

        <div class= "left-container">
            <div class = "booking-container">

                <div class="booking-place">

                    <c:forEach var="booking" items="${bookingSet}">
                        <div class = "booking-unit">
                            <p>

                                <c:out value="Столик: ${booking.getTable()} "/>
                                <c:out value="Время: ${requestScope.timeFormat.format(booking.getDate())} ${booking.getDate().getDate()}  "/>
                                <c:out value="Телефон: ${booking.getPhoneNumber()}          "/>
                                <c:out value="Количество людей: ${booking.getNumberPerson()} "/>
                                <c:out value="Продолжительность ~ ${booking.getNumberPerson()} часа"/>

                            </p>
                        </div>
                    </c:forEach>

                    <div class="booking-add">
                        <form action="add" method="post">
                            <span>Столик<input type="text" name="id_table"></span>
                            <span>Телефон клиента<input type="text" name="phone_number"></span>
                            <span>Дата<input type="date" name="date"></span>
                            <span>Время<input type="time" name="time"></span>
                            <span>Количество<input type = "text" name = "number_person"></span>
                            <span>Продолжительность<input type="text" name = "duration"></span>
                            <span><button name = "entity" value="booking"> Добавить </button></span>
                        </form>
                    </div>

                </div>
            </div>
        </div>
        <div class="right-container">
            <div class = "table-container">
                <div class = "table-text"> Столики </div>
                <form class="table-place" action = "info" method = "get">
                    <input type="hidden" name="entity" value="table">
                        <c:forEach var="table" items="${tableSet}">

                            <button type = "submit" class = "table-unit" name = "id" value="${table.getNumber()}">
                                <p>
                                    <c:out value="Столик #${table.getNumber()} "/>
                                    <c:out value="Место - ${table.getPlacement()} "/>
                                    <c:out value="Вместимость - ${table.getCapacity()} "/>
                                </p>
                            </button>
                        </c:forEach>
                </form>
                <form class = "form-add" action="/add" method="get">
                    <button type="submit" name="entity" value="table">Добавить</button>
                </form>
            </div>
            <div class = "client-container">
                <div class = "table-text"> Посетители </div>
                <form class="table-place" action = "info" method="get">
                    <input type="hidden" name="entity" value="client">
                        <c:forEach var="guest" items="${guestSet}">
                            <input type="hidden" name="entity" value="client">
                            <button class = "table-unit" name = "id" value="${guest.getId()}">
                                <p>
                                    <c:out value="${guest.getFirstName()} "/>
                                    <c:out value="${guest.getLastName()} "/>
                                    <c:out value="Телефон: ${guest.getPhoneNumber()} "/>
                                </p>
                            </button>
                        </c:forEach>
                </form>
                <form class = "form-add" action="/add" method="get">
                    <button type="submit" name="entity" value="guest">Добавить</button>
                </form>
            </div>
        </div>
    </main>
    <c:if test="${requestScope.message != null}">
        <div class = "message-block"><p>${requestScope.message}</p></div>
    </c:if>
    <script><%@include file="/js/jquery-3.6.1.min.js" %></script>
    <script><%@include file="/js/catalog.js" %></script>
</body>
</html>
