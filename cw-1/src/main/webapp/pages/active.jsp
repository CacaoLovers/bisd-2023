<%--
  Created by IntelliJ IDEA.
  User: marsf
  Date: 13.10.2022
  Time: 13:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Активные заказы</title>
</head>
<body>
    Список заказов:
    <form action = "active" method="get">
        <c:forEach var="order" items="${orderList}">
            <p><input type="checkbox" name="${order.getClient()}" value="true"><c:out value="${order.getProduct}"/></p>
        </c:forEach>

        <p><input type="submit" value="Заказать"></p>
    </form>
    ${sessionScope.basket}
</body>
</html>
