<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>GCIT LMS</title>
</head>
<body>
<h1>Edit Publisher</h1>
<h2>Enter New Details</h2>
<form action="editPublisher" method="post">
    <table>
        <tr>
            <td>Enter New Name:</td>
            <td><input type="text" name="name" value="${publisher.getPublisherName()}"></td>
        </tr>
        <tr>
            <td>Enter New Address:</td>
            <td><input type="text" name="address" value="${publisher.getPublisherAddress()}"></td>
        </tr>
        <tr>
            <td>Enter New Phone:</td>
            <td><input type="text" name="phone" value="${publisher.getPublisherPhone()}"></td>
        </tr>
    </table>
    <input type="hidden" name="id" value="${publisher.getPublisherId()}">
    <input type="submit" value="Submit">
</form>
<p>
</body>
</html>