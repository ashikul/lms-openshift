<%@include file="../../modalcss.html" %>
<div class="jumbotron-modal">
<h1>Override Loan</h1>
<h2>Enter Loan Details</h2>
<form action="override" method="post">
    Enter New Due Date:<input type="date" name="dueDate" value="${bookLoan.getDueDate()}">
    <input type="hidden" name="bookId" value="${bookLoan.getBook().getBookId()}">
    <input type="hidden" name="branchId" value="${bookLoan.getLibraryBranch().getBranchId()}">
    <input type="hidden" name="cardNo" value="${bookLoan.getBorrower().getCardNo()}">
    <input type="hidden" name="dateOut" value="${bookLoan.getDateOut()}">
    <input type="submit" value="Submit">
</form>
</div>