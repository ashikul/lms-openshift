<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ page import="com.gcit.lms.domain.Borrower" %>
<%@ page import="com.gcit.lms.service.AdminService" %>
<%@ page import="java.util.List" %>
<%
    AdminService service = (AdminService) request.getAttribute("service");
    int count = service.getBorrowersCount();
    int pages = count / 5;
    if (count % 5 != 0) pages++;

    List<Borrower> lst = service.getAllBorrowers(1, 5);
%>
<%@include file="../../navbar.html" %>
<script>
    $(document).on('hidden.bs.modal', '.modal', function () {
        $(this).removeData('bs.modal');
    });
    function search() {
        $.ajax({
            url: "searchBorrowers",
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
            url: "pageBorrowers",
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


    <div class="jumbotron">
        <h1>Table Borrowers</h1>
        <input type="text" class="col-md-4" id="searchString"
               placeholder="Enter borrower name to search"> <input
            type="button" value="Search" onclick="search();">
        <table class="table">
            <tr>
                <th>Name</th>
                <th>Address</th>
                <th>Phone</th>
            </tr>
            <%
                for (Borrower b : lst) {
            %>
            <tr>
                <td><%=b.getName()%>
                </td>
                <td><%=b.getAddress()%>
                </td>
                <td><%=b.getPhone() != null ? b.getPhone() : ""%>
                </td>
                <td>
                    <button type="button" class="btn btn btn-default"
                            data-toggle="modal" data-target="#myModal1"
                            href="editBorrower?id=<%=b.getCardNo()%>">EDIT
                    </button>
                </td>
                <td>
                    <button type="button" class="btn btn btn-primary"
                            onclick="location.href='deleteBorrower?id=<%=b.getCardNo()%>'">DELETE
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