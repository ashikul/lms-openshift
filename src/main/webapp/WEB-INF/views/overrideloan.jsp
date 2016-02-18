<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>GCIT LMS</title>
</head>
<body>
<h1>Override Loan</h1>
<h2>Enter Loan Details</h2>
<form action="override" method="post">
    Enter New Due Date:<input type="date" name="dueDate" value="${bookLoan.getDueDate()}">
    <input type="hidden" name="bookId" value="${bookLoan.getBook().getBookId()}">
    <input type="hidden" name="branchId" value="${bookLoan.getBranch().getBranchId()}">
    <input type="hidden" name="cardNo" value="${bookLoan.getBorrower().getCardNo()}">
    <input type="hidden" name="dateOut" value="${bookLoan.getDateOut()}">
    <input type="submit" value="Submit">
</form>
<p>
</body>
</html>