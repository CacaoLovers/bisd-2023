<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="listeners.UserRoomListener" %>
<%@ page import="java.util.Iterator" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>

        <title> ${sessionScope.resourceBundle.getString("homePage")}  </title>
        <style><%@include file="/css/index.css" %></style>


    </head>

    <body>
        <div class = "change-lan">

            <form action="/" method="post">

                <button type="submit" name = "language" value="en" style="background-image: url('${pageContext.request.contextPath}/images/eng.jpg')"></button>
                <button type="submit" name = "language" value="ru" style="background-image: url('${pageContext.request.contextPath}/images/russia.jpeg')"></button>
                <button type="submit" name = "language" value="tat" style="background-image: url('${pageContext.request.contextPath}/images/tatar.jpg')"></button>

            </form>

        </div>

        <main>
            <div class="create-room">
                <form class = "create-form" action = "/room" method="post">
                    <button class = "create-button" type="submit" name="id_room" value="create"> ${ sessionScope.resourceBundle.getString("createRoom") } </button>
                </form>
            </div>
            <div class="flip-container" ontouchstart="this.classList.toggle('hover');">

                <div class="flipper">

                    <div class="front">

                        <p> ${ sessionScope.resourceBundle.getString("joinRoom") } </p>

                    </div>

                    <div class="back">

                        <form class="join-form" action="/room" method="get">
                            <p> ${ sessionScope.resourceBundle.getString("enterCode") } </p>
                            <input type="text" name="id_room">
                            <button type="submit"> ${ sessionScope.resourceBundle.getString("join") } </button>
                        </form>

                    </div>

                </div>
            </div>
        </main>
    </body>
</html>