<link href="bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="starter-template.css" rel="stylesheet" type="text/css">
<div class="jumbotron-modal">
<h1>Edit Branch</h1>
<h2>Enter New Details</h2>
<form action="editBranch" method="post">
    <table>
        <tr>
            <td>Enter New Name:</td>
            <td><input type="text" name="name" value="${libraryBranch.getBranchName()}"></td>
        </tr>
        <tr>
            <td>Enter New Address:</td>
            <td><input type="text" name="address" value="${libraryBranch.getBranchAddress()}"></td>
        </tr>
    </table>
    <input type="hidden" name="id" value="${libraryBranch.getBranchId()}">
    <input type="hidden" name="forward"
           value=<%= request.getParameter("forward") == null ? "" : request.getParameter("forward")%>>
    <input type="submit" value="Submit">
</form>
</div>