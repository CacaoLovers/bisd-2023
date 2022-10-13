<%--
  Created by IntelliJ IDEA.
  User: marsf
  Date: 13.10.2022
  Time: 12:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Заказ</title>
</head>
<body>

    <form action = "active" method="get">
        <c:forEach var="order" items="${orderList}">
            <p><input type="checkbox" name="${order.getName()}"><c:out value="${order.getName()}"/></p>
        </c:forEach>
        <p><input type="submit" value="Заказать"></p>
    </form>

</body>
</html>
