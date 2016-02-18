<%@include file="template.html" %>
<div class="container theme-showcase" role="main">
    <!-- Main jumbotron for a primary marketing message or call to action -->
    <div class="jumbotron">
        <h1>Add Branch</h1>
        <h2>Enter Branch Details</h2>
        <form action="addBranch" method="post">
            <table>
                <tr>
                    <td>Enter Branch Name:</td>
                    <td><input type="text" name="name"><br/></td>
                </tr>
                <tr>
                    <td>Enter Branch Address:</td>
                    <td><input type="text" name="address"><br/></td>
                </tr>
            </table>
            <input type="submit" value="Submit">
        </form>
    </div>
</div>