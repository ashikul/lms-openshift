<link href="bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="starter-template.css" rel="stylesheet" type="text/css">
<div class="jumbotron-modal">
<h1>Override Loan</h1>
<h2>Enter Loan Details</h2>
<form action="override" method="post">
    Enter New Due Date:<input type="date" name="dueDate" value="${loan.getDueDate()}">
    <input type="hidden" name="bookId" value="${loan.getBook().getBookId()}">
    <input type="hidden" name="branchId" value="${loan.getLibraryBranch().getBranchId()}">
    <input type="hidden" name="cardNo" value="${loan.getBorrower().getCardNo()}">
    <input type="hidden" name="dateOut" value="${loan.getDateOut()}">
    <input type="submit" value="Submit">
</form>
</div>