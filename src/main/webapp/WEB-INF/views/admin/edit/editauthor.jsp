<link href="bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="starter-template.css" rel="stylesheet" type="text/css">
<div class="jumbotron-modal">
<h1>Edit Author</h1>
<h2>Enter Author Details</h2>
<form action="editAuthor" method="post">
    Enter New Author Name: <input type="text" name="authorName" value="${author.getAuthorName()}">
    <input type="hidden" name="authorId" value="${author.getAuthorId()}">
    <input type="submit" value="Submit">
</form>
</div>
