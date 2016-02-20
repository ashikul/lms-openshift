<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ page import="com.gcit.lms.domain.Author" %>
<%@ page import="com.gcit.lms.domain.Book" %>
<%@ page import="com.gcit.lms.domain.Genre" %>
<%@ page import="com.gcit.lms.service.LibrarianService" %>
<%@ page import="java.util.List" %>
<%
    LibrarianService service = (LibrarianService) request.getAttribute("service");
    int count = service.getBooksCount();
    int pages = count / 5;
    if (count % 5 != 0)
        pages++;

    int maxBook = 0;
    int maxGenre = 0;
    List<Book> books = service.getAllBooks(1, 5);

    for (Book b : books) {
        if (maxBook < b.getAuthors().size())
            maxBook = b.getAuthors().size();
        if (maxGenre < b.getGenres().size())
            maxGenre = b.getGenres().size();
    }
%>
<%@include file="../header.html" %>
<script>
    $(document).on('hidden.bs.modal', '.modal', function () {
        $(this).removeData('bs.modal');
    });
    function search() {
        $.ajax({
            url: "searchLibBooks",
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
            url: "pageLibBooks",
            data: {
                searchString: $('#searchString').val(),
                pageNo: page,
                branchId: <%= request.getParameter("branchId") %>
            }
        }).done(function (data) {
            $('.table').html(data);
        });
    }
</script>
<div class="container theme-showcase" role="main">


    <div class="jumbotron">
        <h1>View Existing Books</h1>
        <h2>Pick the Book you want to add bookCopies of, to your libraryBranch:</h2>
        <input type="text" class="col-md-4" id="searchString"
               placeholder="Enter libraryBranch name to search"> <input
            type="button" value="Search" onclick="search();">
        <table class="table">
            <tr>
                <th>Title</th>
                <th colspan="<%=maxBook%>">Author(s)</th>
                <th>Publisher</th>
                <th colspan="<%=maxGenre%>">Genre(s)</th>
            </tr>
            <%
                for (Book b : books) {
            %>
            <tr>
                <td><%=b.getTitle()%>
                </td>
                <%
                    for (Author a : b.getAuthors()) {
                %>
                <td><%=a.getAuthorName()%>
                </td>
                <%
                    }
                %>
                <%
                    for (int i = b.getAuthors().size(); i < maxBook; i++) {
                %>
                <td></td>
                <%
                    }
                %>
                <td><%=b.getPublisher().getPublisherName()%>
                </td>
                <%
                    for (Genre g : b.getGenres()) {
                %>
                <td><%=g.getGenreName()%>
                </td>
                <%
                    }
                %>
                <%
                    for (int i = b.getGenres().size(); i < maxGenre; i++) {
                %>
                <td></td>
                <%
                    }
                %>
                <td>
                    <button type="button" class="btn btn btn-info"
                            data-toggle="modal" data-target="#myModal1"
                            href="getCopies?branchId=<%=request.getParameter("branchId")%>&bookId=<%=b.getBookId()%>">
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
        <p>${message}</p>
    </div>
</div>

<div id="myModal1" class="modal fade" tabindex="-1" role="dialog"
     aria-labelledby="myLargeModalLabel">
    <div class="modal-dialog modal-lg">
        <div class="modal-content"></div>
    </div>
</div>