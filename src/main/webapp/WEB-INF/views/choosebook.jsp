<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>GCIT LMS</title>
</head>
<body>
<h1>You have chosen Branch: ${copies.getBranch().getBranchName()}, ${copies.getBranch().getBranchAddress()}</h1>
<h1>Book Title: ${copies.getBook().getTitle()}</h1>
<h2>Current number of copies: ${copies.getNoOfCopies()}</h2>
<form action="updateCopies" method="post">
    Enter new number of copies: <input type="text" name="newCopies">
    <input type="hidden" name="oldCopies" value="${copies.getNoOfCopies()}">
    <input type="hidden" name="branchId" value="${copies.getBranch().getBranchId()}">
    <input type="hidden" name="name" value="${copies.getBranch().getBranchName()}">
    <input type="hidden" name="address" value="${copies.getBranch().getBranchAddress()}">
    <input type="hidden" name="bookId" value="${copies.getBook().getBookId()}">
    <input type="submit" value="Submit">
</form>
</body>
</html>