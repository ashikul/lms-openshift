<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ page import="com.gcit.lms.domain.Author" %>
<%@ page import="com.gcit.lms.service.AdminService" %>
<%@ page import="java.util.List" %>
<%
    AdminService service = (AdminService) request.getAttribute("service");
    int count = service.getAuthorsCount();
    int pages = count / 5;
    if (count % 5 != 0) pages++;

    List<Author> authors = service.getAllAuthors(1, 5);
%>
<%@include file="../navbar.html" %>
<script>
    $(document).on('hidden.bs.modal', '.modal', function () {
        $(this).removeData('bs.modal');
    });
    function search() {
        $.ajax({
            url: "searchAuthors",
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
            url: "pageAuthors",
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
        <h1>View Existing Authors</h1>

        <div class="container">
            <div class="row">
                <div class="col-md-8">
                    <input type="text" class="col-md-4" id="searchString"
                           placeholder="Enter author name to search"> <input
                        type="button" value="Search" onclick="search();">

                </div>
                <div class="col-md-4">
                    <button type="button" class="btn btn btn-info"
                            data-toggle="modal" data-target="#myModal1"
                            href="addAuthor">ADD
                    </button>

                </div>

            </div>

        </div>

        <table class="table">
            <tr>
                <th>Name</th>
            </tr>
            <%
                for (Author a : authors) {
            %>
            <tr>
                <td><%=a.getAuthorName()%>
                </td>
                <td>
                    <button type="button" class="btn btn btn-default"
                            data-toggle="modal" data-target="#myModal1"
                            href="editAuthor?authorId=<%=a.getAuthorId()%>">EDIT
                    </button>
                </td>
                <td>
                    <button type="button" class="btn btn btn-primary"
                            onclick="location.href='deleteAuthor?authorId=<%=a.getAuthorId()%>'">DELETE
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
<%--<div id="myModal2" class="modal fade" tabindex="-1" role="dialog"--%>
<%--aria-labelledby="myLargeModalLabel">--%>
<%--<div class="modal-dialog modal-lg">--%>
<%--<div class="modal-content"></div>--%>
<%--</div>--%>
<%--</div>--%>