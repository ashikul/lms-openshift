<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>GCIT LMS</title>
</head>
<body>
<h1>You have chosen Branch: ${c.getBranch().getBranchName()}, ${c.getBranch().getBranchAddress()}</h1>
<h1>Book Title: ${c.getBook().getTitle()}</h1>
<h2>Current number of c: ${c.getNoOfCopies()}</h2>
<form action="updateCopies" method="post">
    Enter new number of c: <input type="text" name="newCopies">
    <input type="hidden" name="oldCopies" value="${c.getNoOfCopies()}">
    <input type="hidden" name="branchId" value="${c.getBranch().getBranchId()}">
    <input type="hidden" name="name" value="${c.getBranch().getBranchName()}">
    <input type="hidden" name="address" value="${c.getBranch().getBranchAddress()}">
    <input type="hidden" name="bookId" value="${c.getBook().getBookId()}">
    <input type="submit" value="Submit">
</form>
</body>
</html>