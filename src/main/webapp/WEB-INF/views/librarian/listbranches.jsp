<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ page import="com.gcit.lms.domain.LibraryBranch" %>
<%@ page import="com.gcit.lms.service.LibrarianService" %>
<%@ page import="java.util.List" %>
<%
    LibrarianService service = (LibrarianService) request.getAttribute("service");
    int count = service.getBranchesCount();
    int pages = count / 5;
    if (count % 5 != 0)
        pages++;

    List<LibraryBranch> lst = service.getAllBranches(1, 5);
%>
<%@include file="../navbar.html" %>
<script>
    function search() {
        $.ajax({
            url: "searchLibBranches",
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
            url: "pageLibBranches",
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
        <h1>Table Branches</h1>
        <input type="text" class="col-md-4" id="searchString"
               placeholder="Enter libraryBranch name to search"> <input
            type="button" value="Search" onclick="search();">
        <h2>Choose Branch you manage:</h2>
        <table class="table">
            <tr>
                <th>Name</th>
                <th>Address</th>
            </tr>
            <%
                for (LibraryBranch b : lst) {
            %>
            <tr>
                <td><%=b.getBranchName()%>
                </td>
                <td><%=b.getBranchAddress()%>
                </td>
                <td>
                    <button type="button" class="btn btn btn-info"
                            onclick="location.href='choosebranch?id=<%=b.getBranchId()%>&name=<%=b.getBranchName()%>&address=<%=b.getBranchAddress()%>'">
                        Choose
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
    </div>
</div>