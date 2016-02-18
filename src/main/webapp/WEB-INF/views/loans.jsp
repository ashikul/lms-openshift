<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ page import="com.gcit.lms.domain.BookLoan" %>
<%@ page import="com.gcit.lms.service.AdminService" %>
<%@ page import="java.util.List" %>
<%
    AdminService service = (AdminService) request.getAttribute("service");
    int count = service.getLoansCount();
    int pages = count / 5;
    if (count % 5 != 0) pages++;

    List<BookLoan> lst = service.getAllLoans(1, 5);
%>
<%@include file="template.html" %>
<script>
    $(document).on('hidden.bs.modal', '.modal', function () {
        $(this).removeData('bs.modal');
    });
    function search() {
        $.ajax({
            url: "searchLoans",
            data: {
                searchString: $('#searchString').val()
            }
        }).done(function (data) {
            $('.pagination').html(data);
            paging(1);
        });
    }
    function paging(page) {
        $.ajax({
            url: "pageLoans",
            data: {
                searchString: $('#searchString').val(),
                pageNo: page
            }
        }).done(function (data) {
            $('.table').html(data);
        });
    }
</script>
<div class="container theme-showcase" role="main">

    <!-- Main jumbotron for a primary marketing message or call to action -->
    <div class="jumbotron">
        <h1>View Existing Book Loans</h1>
        <input type="text" class="col-md-4" id="searchString"
               placeholder="Enter due date to search"> <input
            type="button" value="Search" onclick="search();">
        <table class="table">
            <tr>
                <th>Borrower Name</th>
                <th>Book Title</th>
                <th>Branch Name</th>
                <th>Branch Address</th>
                <th>Date Out</th>
                <th>Due Date</th>
            </tr>
            <%
                for (BookLoan l : lst) {
            %>
            <tr>
                <td><%=l.getBorrower().getName()%>
                </td>
                <td><%=l.getBook().getTitle()%>
                </td>
                <td><%=l.getLibraryBranch().getBranchName()%>
                </td>
                <td><%=l.getLibraryBranch().getBranchAddress()%>
                </td>
                <td><%=l.getDateOut().toString()%>
                </td>
                <td><%=l.getDueDate().toString()%>
                </td>
                <td>
                    <button type="button" class="btn btn btn-primary"
                            data-toggle="modal" data-target="#myModal1"
                            href="overrideloan?bookId=<%=l.getBook().getBookId()%>&branchId=<%=l.getLibraryBranch().getBranchId()%>&cardNo=<%=l.getBorrower().getCardNo()%>&dateOut=<%=l.getDateOut().toString()%>&dueDate=<%=l.getDueDate().toString()%>">
                        OVERRIDE
                    </button>
                </td>
            </tr>
            <%
                }
            %>
        </table>
        <nav>
            <ul class="pagination">
                <%
                    for (int i = 1; i <= pages; i++) {
                %>
                <li><a id="page" href="#" onclick="paging(<%=i%>);"><%=i%>
                </a></li>
                <%
                    }
                %>
            </ul>
        </nav>
        <p>${message}</p>
    </div>
</div>
<div id="myModal1" class="modal fade" tabindex="-1" role="dialog"
     aria-labelledby="myLargeModalLabel">
    <div class="modal-dialog modal-lg">
        <div class="modal-content"></div>
    </div>
</div>