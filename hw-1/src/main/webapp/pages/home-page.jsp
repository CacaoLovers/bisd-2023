<%@ page import="java.util.Iterator" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.io.InputStream" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Home page</title>
    <link href="../css/home-page.css" rel="stylesheet" type="text/css">
</head>
<body>

<header>
    <div class = "header-text">forward to experience</div>
</header>
<img class = "header-img" src = "../images/horse.gif" alt="Horse">
<main>
    <div class = "page-text">
        <span>Brutal</span>
        <span>text</span>
        <span>on</span>
        <span>my Page</span></div>
</main>

<a class="border-button" href = "/contents"><image src = "../images/general-button.png"></image></a>
<%--На разных устройствах фото из /WEB-INF/image/.. выгружается с временным успехом :( --%>
<%--<img src="${pageContext.request.contextPath}/images/general-button.png">--%>
</body>
</html>