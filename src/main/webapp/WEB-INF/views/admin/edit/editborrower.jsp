<%@include file="../../modalcss.html" %>
<div class="jumbotron-modal">
<h1>Edit Borrower</h1>
<h2>Enter New Details</h2>
<form action="editBorrower" method="post">
    <table>
        <tr>
            <td>Enter New Name:</td>
            <td><input type="text" name="name" value="${borrower.getName()}"></td>
        </tr>
        <tr>
            <td>Enter New Address:</td>
            <td><input type="text" name="address" value="${borrower.getAddress()}"></td>
        </tr>
        <tr>
            <td>Enter New Phone:</td>
            <td><input type="text" name="phone" value="${borrower.getPhone()}"></td>
        </tr>
    </table>
    <input type="hidden" name="id" value="${borrower.getCardNo()}">
    <input type="submit" value="Submit">
</form>
</div>