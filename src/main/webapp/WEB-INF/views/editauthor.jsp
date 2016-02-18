<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>GCIT LMS</title>
</head>
<body>
<h1>Edit Author</h1>
<h2>Enter Author Details</h2>
<form action="editAuthor" method="post">
    Enter New Author Name: <input type="text" name="authorName" value="${author.getAuthorName()}">
    <input type="hidden" name="authorId" value="${author.getAuthorId()}">
    <input type="submit" value="Submit">
</form>
<p>
</body>
</html>