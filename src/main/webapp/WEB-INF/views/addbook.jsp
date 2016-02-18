<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ page import="com.gcit.lms.domain.Author" %>
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
<%@include file="template.html" %>
<div class="container theme-showcase" role="main">

    <!-- Main jumbotron for a primary marketing message or call to action -->
    <div class="jumbotron">
        <h1>Add Book</h1>
        <h2>Enter Book Details</h2>
        <form action="addBook" method="post">
            <table class="table">
                <tr>
                    <td>Enter Book Title:</td>
                    <td><input type="text" name="title"></td>
                </tr>
                <tr>
                    <td>Choose Author:</td>
                    <td><select name="authorId" multiple>
                        <%
                            for (Author a : authors) {
                        %>
                        <option value="<%=a.getAuthorId()%>"><%=a.getAuthorName()%>
                        </option>
                        <%
                            }
                        %>
                    </select></td>
                </tr>
                <tr>
                    <td>Choose Publisher:</td>
                    <td><select name="pubId">
                        <%
                            for (Publisher p : publishers) {
                        %>
                        <option value="<%=p.getPublisherId()%>"><%=p.getPublisherName()%>
                        </option>
                        <%
                            }
                        %>
                    </select></td>
                </tr>
                <tr>
                    <td>Choose Genre:</td>
                    <td><select name="genre_id" multiple>
                        <%
                            for (Genre g : genres) {
                        %>
                        <option value="<%=g.getGenreId()%>"><%=g.getGenreName()%>
                        </option>
                        <%
                            }
                        %>
                    </select></td>
                </tr>
            </table>
            <input type="submit" value="Submit">
        </form>
    </div>
</div>
