<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>CRUD DEMO</title>
</head>

<body>
<h2 style="text-align: center; color: blue">CRUD DEMO</h2>
<hr>
<h3 style="text-align: center; color: red;">${errors}</h3>
<form id="frmTodo" name="frmTodo" method="post" action="todo">
    <table border="0" align="center" cellpadding="5" cellspacing="0">
        <tr>
            <td>Todo Id</td>
            <td><input readonly type="text" name="id" value="${todo.Id}"/></td>
        </tr>
        <tr>
            <td>Name</td>
            <td><input size="44" type="text" name="name" value="${todo.Name}" /></td>
        </tr>
        <tr>
            <td>Description</td>
            <td><textarea rows="2" cols="44" name="description">${todo.Description}</textarea></td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td>
                <input type="submit" name="btnInsert" id="btnInsert" value="Insert" />
                <input type="submit" name="btnUpdate" id="btnUpdate" value="Update" />
                <input type="submit" name="btnDelete" id="btnDelete" value="Delete" />
            </td>
        </tr>
    </table>
    <hr/>

    <table width="100%" border="1" align="center" cellpadding="5" cellspacing="0">
        <tr bgcolor="lightgray">
            <th>Id</th>
            <th>Name</th>
            <th>Description</th>
            <th>&nbsp;</th>
        </tr>
        <c:forEach var="todo" items="${todos}">
            <tr>
                <td>${todo.getId()}</td>
                <td>${todo.getName()}</td>
                <td>${todo.getDescription()}</td>
                <td><a href="?lnkEdit&txtId=${todo.getId()}">Edit</a></td>
            </tr>
        </c:forEach>
    </table>
</form>
</body>
</html>