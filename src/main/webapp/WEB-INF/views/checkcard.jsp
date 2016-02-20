<%@include file="header.html" %>
<div class="container theme-showcase" role="main">


    <div class="jumbotron">
        <h1>Hello Borrower</h1>
        <h2>Enter the your Card Number</h2>
        <form action="validateCard" method="post">
            Enter Card No: <input type="text" name="cardNo"> <input type="submit" value="Submit">
        </form>
        <p>
        <p>${message}</p>
    </div>
</div>