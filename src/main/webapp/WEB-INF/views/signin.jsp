<%@include file="header.html" %>
<div class="container theme-showcase" role="main">

    <!-- Main jumbotron for a primary marketing message or call to action -->
    <div class="jumbotron">
        <h1>Welcome Borrower</h1>
        <h2>Your Card Number is: <%=request.getParameter("cardNo")%>
        </h2>
        <ul class="nav nav-pills nav-stacked">
            <li><a
                    href="checkoutbranch?cardNo=<%=request.getParameter("cardNo")%>">Check
                out a book</a></li>
            <li><a
                    href="checkinbranch?cardNo=<%=request.getParameter("cardNo")%>">Return
                a Book</a></li>
        </ul>
        <p>${message}</p>
    </div>
</div>