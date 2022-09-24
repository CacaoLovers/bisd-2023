<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.example.demo.Content" %>
<%@ page import="java.util.Arrays" %>
<%--
  Created by IntelliJ IDEA.
  User: marsf
  Date: 06.09.2022
  Time: 21:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Content</title>
    <link href="../css/content-container.css" rel="stylesheet" type="text/css">
    <style>
        html, body{
            background-color: black;
            width: 100%;
            height: 100%;
            padding: 0;
            margin: 0;
            font-family: Calibri;
        }

        main{
            margin: auto;
            width: 75%;
            height: 95%;
            justify-content: center;
            padding-top: 2%;
        }

        .content-container{

            color: white;
            font-size: 150%;
            height: 100%;
            width: 100%;
            display: inline-block;
            text-decoration: none;
            margin-top: 20px;
            margin-right: 10px;
            border: 1px solid white;
            background-color: black;
            text-align: center;
            vertical-align: center;
            object-fit: none;

        }
        .content-container:hover{
            transform: scale(1.1);
            animation: move-background 2s ease forwards;
            background-repeat: no-repeat;
            background-size: cover;
            background-origin: content-box;

        }


        @keyframes move-background {
            0%{
                background-position: 0px;
            }
            100%{
                background-position: -50px;

            }
        }

        .content-form{
            display: inline-block;
            height: 90%;
            width: 31%;
            padding-right: 10px;
        }

        .content-form input{
            cursor: pointer;
        }


    </style>
</head>
<body>

    <%
            PrintWriter htmlOutput = response.getWriter();
            Map<String, ? extends ServletRegistration> servletMap = request.getServletContext().getServletRegistrations();
            htmlOutput.println("<main>");


        for(ServletRegistration servletRegistration: servletMap.values()){
            try{
                Class<?> servletClass = Class.forName(servletRegistration.getClassName());
                if (servletClass.isAnnotationPresent(Content.class)) {
                    String mapping = servletRegistration.getMappings().iterator().next();
                    htmlOutput.println("<form class = \"content-form\" action = \""+ mapping +"\" method = \"get\">");
                    htmlOutput.println("<input type = \"submit\" onmouseover = \"this.style.backgroundImage = 'url("
                            + servletClass.getAnnotation(Content.class).photo()
                            + ")'; \" onmouseout = \" this.style.backgroundImage = 'none';\" class = \"content-container\""
                            + "value = \"" + servletClass.getAnnotation(Content.class).contentField() + "\" >" );
                    htmlOutput.println("</form>");
                }

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        htmlOutput.println("</main>");
    %>
    </main>
</body>
</html>
