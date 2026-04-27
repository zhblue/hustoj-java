<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Privileges</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/katex.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/header.jsp"/>

<div class="container mt-4"><div class="container mt-4">
    <h2>Manage Privileges</h2>
    <table class="table table-striped">
        <thead><tr><th>User</th><th>Right</th><th>Value</th><th>Action</th></tr></thead>
        <tbody>
            <c:forEach var="p" items="${privileges}">
                <tr>
                    <td>${p.userId}</td>
                    <td>${p.rightStr}</td>
                    <td>${p.valueStr}</td>
                    <td><a href="${pageContext.request.contextPath}/admin/privilege-delete?user_id=${p.userId}&rightstr=${p.rightStr}">Delete</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <h3>Add Privilege</h3>
    <form method="post" action="${pageContext.request.contextPath}/admin/privilege-add">
        <div class="mb-3">
            <input type="text" name="user_id" placeholder="User ID" required/>
            <input type="text" name="rightstr" placeholder="Right (e.g. administrator)" required/>
            <button type="submit" class="btn btn-primary">Add</button>
        </div>
    </form>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
</body>
</html>
