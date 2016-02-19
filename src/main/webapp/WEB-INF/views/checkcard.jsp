<%@include file="header.html" %>
<div class="container theme-showcase" role="main">

    <!-- Main jumbotron for a primary marketing message or call to action -->
    <div class="jumbotron">
        <h1>Welcome Borrower</h1>
        <h2>Enter the your Card Number</h2>
        <form action="validateCard" method="post">
            Enter Card No: <input type="text" name="cardNo"> <input type="submit" value="Submit">
        </form>
        <p>
        <p>${message}</p>
    </div>
</div>