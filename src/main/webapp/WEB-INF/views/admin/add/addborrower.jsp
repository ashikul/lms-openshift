<link href="bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="starter-template.css" rel="stylesheet" type="text/css">
<div class="jumbotron-modal">
    <h1>Add Borrower</h1>
    <h2>Enter Borrower Details</h2>
    <form action="addBorrower" method="post">
        <table>
            <tr>
                <td>Enter Borrower Name:</td>
                <td><input type="text" name="name"><br/></td>
            </tr>
            <tr>
                <td>Enter Borrower Address:</td>
                <td><input type="text" name="address"><br/></td>
            </tr>
            <tr>
                <td>Enter Borrower Phone:</td>
                <td><input type="text" name="phone"><br/></td>
            </tr>
        </table>
        <input type="submit" value="Submit">
    </form>
</div>
