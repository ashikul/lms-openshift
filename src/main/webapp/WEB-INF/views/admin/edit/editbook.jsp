<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ page import="com.gcit.lms.domain.Author" %>
<%@ page import="com.gcit.lms.domain.Book" %>
<%@ page import="com.gcit.lms.domain.Genre" %>
<%@ page import="com.gcit.lms.domain.Publisher" %>
<%@ page import="com.gcit.lms.service.AdminService" %>
<%@ page import="java.util.List" %>
<%
    AdminService service = (AdminService) request.getAttribute("service");
    List<Author> authors = service.getAllAuthors(-1, 5);
    List<Publisher> publishers = service.getAllPublishers(-1, 5);
    List<Genre> genres = service.getAllGenres();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>GCIT LMS</title>
</head>
<body>
<h1>Edit Book</h1>
<h2>Enter Book Details</h2>
<form action="editBook" method="post">
    <table>
        <tr>
            <td>Enter New Book Title:</td>
            <td><input type="text" name="title" value="${book.getTitle()}"></td>
        </tr>
        <tr>
            <td>Choose New Author:</td>
            <td>
                <select name="authorId" multiple>
                    <%for (Author a : authors) { %>
                    <option <%if(((Book)request.getAttribute("book")).getAuthors().contains(a)) { %>selected<%} %>
                            value="<%=a.getAuthorId()%>"><%=a.getAuthorName()%>
                    </option>
                    <%} %>
                </select>
            </td>
        </tr>
        <tr>
            <td>Choose New Publisher:</td>
            <td>
                <select name="pubId">
                    <%for (Publisher p : publishers) { %>
                    <option <%if(((Book)request.getAttribute("book")).getPublisher().equals(p)) { %>selected<%} %>
                            value="<%=p.getPublisherId()%>"><%=p.getPublisherName()%>
                    </option>
                    <%} %>
                </select>
            </td>
        </tr>
        <tr>
            <td>Choose New Genre:</td>
            <td>
                <select name="genre_id" multiple>
                    <%for (Genre g : genres) { %>
                    <option <%if(((Book)request.getAttribute("book")).getGenres().contains(g)) { %>selected<%} %>
                            value="<%=g.getGenreId()%>"><%=g.getGenreName()%>
                    </option>
                    <%} %>
                </select>
            </td>
        </tr>
    </table>
    <input type="hidden" name="bookId" value="${book.getBookId()}">
    <input type="submit" value="Submit">
</form>
<p>
</body>
</html>