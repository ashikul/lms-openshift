<%@include file="../modalcss.html" %>
<div class="jumbotron-modal">
<h1>You have chosen
    Branch: ${bookCopies.getLibraryBranch().getBranchName()}, ${bookCopies.getLibraryBranch().getBranchAddress()}</h1>
<h1>Book Title: ${bookCopies.getBook().getTitle()}</h1>
<h2>Current number of bookCopies: ${bookCopies.getNoOfCopies()}</h2>
<form action="updateCopies" method="post">
    Enter new number of bookCopies: <input type="text" name="newCopies">
    <input type="hidden" name="oldCopies" value="${bookCopies.getNoOfCopies()}">
    <input type="hidden" name="branchId" value="${bookCopies.getLibraryBranch().getBranchId()}">
    <input type="hidden" name="name" value="${bookCopies.getLibraryBranch().getBranchName()}">
    <input type="hidden" name="address" value="${bookCopies.getLibraryBranch().getBranchAddress()}">
    <input type="hidden" name="bookId" value="${bookCopies.getBook().getBookId()}">
    <input type="submit" value="Submit">
</form>
</div>