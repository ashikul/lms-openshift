<%@include file="template.html" %>
<div class="container theme-showcase" role="main">
    <!-- Main jumbotron for a primary marketing message or call to action -->
    <div class="jumbotron">
        <h1>Publisher Management</h1>
        <h2>Pick your option:</h2>
        <ul class="nav nav-pills nav-stacked">
            <li><a href="addpublisher">Add Publisher</a></li>
            <li><a href="viewpublishers">View Publishers</a></li>
            <li><a href="admin">Quit to previous</a></li>
        </ul>
        <p>${message}</p>
    </div>
</div>