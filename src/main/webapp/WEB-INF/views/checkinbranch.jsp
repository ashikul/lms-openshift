<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ page import="com.gcit.lms.domain.LibraryBranch" %>
<%@ page import="com.gcit.lms.service.BorrowerService" %>
<%@ page import="java.util.List" %>
<%
    BorrowerService service = (BorrowerService) request.getAttribute("service");
    int cardNo = Integer.parseInt(request.getParameter("cardNo"));
    int count = service.getBranchesByCardNoCount(cardNo);
    int pages = count / 5;
    if (count % 5 != 0) pages++;

    List<LibraryBranch> lst = service.getAllBranchesByCardNo(cardNo);
%>
<%@include file="header.html" %>
<script>
    function search() {
        $.ajax({
            url: "searchCheckinBranches",
            data: {
                searchString: $('#searchString').val(),
                cardNo: <%= cardNo %>
            }
        }).done(function (data) {
            $('.pagination').html(data);
            paging(1);
        });
    }
    function paging(page) {
        $.ajax({
            url: "pageCheckinBranches",
            data: {
                searchString: $('#searchString').val(),
                pageNo: page,
                cardNo: <%= cardNo %>
            }
        }).done(function (data) {
            $('.table').html(data);
        });
    }
</script>
<div class="container theme-showcase" role="main">


    <div class="jumbotron">
        <h1>View Existing Branches</h1>
        <h2>Pick the Branch you want to check out from</h2>
        <input type="text" class="col-md-4" id="searchString"
               placeholder="Enter libraryBranch name to search"> <input
            type="button" value="Search" onclick="search();">
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
                <td><%=b.getBranchAddress() != null ? b.getBranchAddress() : ""%>
                </td>
                <td>
                    <button type="button" class="btn btn btn-info"
                            onclick="location.href='checkinbook?cardNo=<%=request.getParameter("cardNo")%>&branchId=<%=b.getBranchId()%>'">
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