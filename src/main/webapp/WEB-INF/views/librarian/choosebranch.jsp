<%@include file="../navbar.html" %>
<script>
    $(document).on('hidden.bs.modal', '.modal', function () {
        $(this).removeData('bs.modal');
    });
</script>
<div class="container theme-showcase" role="main">


    <div class="jumbotron">
        <h1>
            You have chosen Branch:</br>
            <%=request.getParameter("name")%>,
            <%=request.getParameter("address")%>
        </h1>
        <h2>Choose an option:</h2>
        <ul class="nav nav-pills nav-stacked">
            <li><a data-toggle="modal" data-target="#myModal1"
                   href="editBranch?id=<%=request.getParameter("id")%>&forward=choosebranch">Update library branch
                info</a></li>
            <li><a
                    href="listbooks?branchId=<%=request.getParameter("id")%>">Update book copies</a></li>
            <%--<li><a href="listbranches">Quit to previous</a></li>--%>
        </ul>
        <p>${message}</p>
    </div>
</div>

<div id="myModal1" class="modal fade" tabindex="-1" role="dialog"
     aria-labelledby="myLargeModalLabel">
    <div class="modal-dialog modal-lg">
        <div class="modal-content"></div>
    </div>
</div>