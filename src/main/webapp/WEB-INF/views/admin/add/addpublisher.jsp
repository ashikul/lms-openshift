<%@include file="../../navbar.html" %>
<div class="container theme-showcase" role="main">

    <div class="jumbotron">
        <h1>Add Publisher</h1>
        <h2>Enter Publisher Details</h2>
        <form action="addPublisher" method="post">
            <table>
                <tr>
                    <td>Enter Publisher Name:</td>
                    <td><input type="text" name="name"><br/></td>
                </tr>
                <tr>
                    <td>Enter Publisher Address:</td>
                    <td><input type="text" name="address"><br/></td>
                </tr>
                <tr>
                    <td>Enter Publisher Phone:</td>
                    <td><input type="text" name="phone"><br/></td>
                </tr>
            </table>
            <input type="submit" value="Submit">
        </form>
    </div>
</div>