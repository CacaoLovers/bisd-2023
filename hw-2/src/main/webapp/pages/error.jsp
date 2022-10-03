<%--
  Created by IntelliJ IDEA.
  User: marsf
  Date: 25.09.2022
  Time: 18:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Error</title>
        <style><%@include file="/css/error.css" %></style>
    </head>
    <body>
        <main>

            <div class = "error-message">

                <p style="color: #4f4e4e">Ошибка</p>
                <p> ${ sessionScope.messageError } </p>

            </div>

            <div class = "back-button">
                <a href = "/">Назад</a>
            </div>

        </main>
    </body>
</html>
