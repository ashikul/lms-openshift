<%--<%@include file="../header.html" %>--%>
<%--<div class="container theme-showcase" role="main">--%>

<%--<!-- Main jumbotron for a primary marketing message or call to action -->--%>
<%--<div class="jumbotron">--%>
<%--<h1>Add Author</h1>--%>
<%--<h2>Enter Author Details</h2>--%>
<%--<form action="addAuthor" method="post">--%>
<%--Enter Author Name: <input type="text" name="authorName"> <input type="submit" value="Submit">--%>
<%--</form>--%>
<%--</div>--%>
<%--</div>--%>
<%--<!DOCTYPE html>--%>
<%--<html>--%>
<%--<head>--%>
<%--</head>--%>
<%--<body>--%>
<h1>Add Author</h1>
<h2>Enter Author Details</h2>
<form action="addAuthor" method="post">
    Enter New Author Name: <input type="text" name="authorName" value="${author.getAuthorName()}">
    <input type="submit" value="Submit">
</form>
<%--<p>--%>
<%--</body>--%>
<%--</html>--%>