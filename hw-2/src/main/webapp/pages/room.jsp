<%@ page import="java.util.List" %>
<%@ page import="models.chat.ChatRoom" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>


<%--
  Created by IntelliJ IDEA.
  User: marsf
  Date: 24.09.2022
  Time: 23:07
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

    <head>

        <title>${sessionScope.resourceBundle.getString("room")} ${sessionScope.currentRoom} </title>
        <style><%@include file="/css/room.css" %></style>
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>

        <script>

            $(document).ready(function() {
                $('.chat-panel').scrollTop($('.chat-panel')[0].scrollHeight);

            });

        </script>



    </head>
    <body>
        <div class="fix-box">
            <span class = "back-button">
                <a href = "/"> « </a>
            </span>
            <span class = "fix-text">

                 ${ sessionScope.resourceBundle.getString("room") } <b style="color: #6b6b6b;"> ${ sessionScope.currentRoom } </b>

            </span>
        </div>
        <main>

            <div class="chat-panel">
                ${ requestScope.chatBlock }
            </div>
            <form class = "send-form" action = "/room" method="get">
                <input class="input-form" type = "text" name = "text_message" placeholder= "${ requestScope.placeholder }" >
                <button class="button-form" type = "submit">  ${ sessionScope.resourceBundle.getString("send") } </button>
            </form>
        </main>
    </body>
</html>
