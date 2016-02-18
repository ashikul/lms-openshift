<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>GCIT LMS</title>
</head>
<body>
<h1>Edit Branch</h1>
<h2>Enter New Details</h2>
<form action="editBranch" method="post">
    <table>
        <tr>
            <td>Enter New Name:</td>
            <td><input type="text" name="name" value="${branch.getBranchName()}"></td>
        </tr>
        <tr>
            <td>Enter New Address:</td>
            <td><input type="text" name="address" value="${branch.getBranchAddress()}"></td>
        </tr>
    </table>
    <input type="hidden" name="id" value="${branch.getBranchId()}">
    <input type="hidden" name="forward"
           value=<%= request.getParameter("forward") == null ? "" : request.getParameter("forward")%>>
    <input type="submit" value="Submit">
</form>
</body>
</html>