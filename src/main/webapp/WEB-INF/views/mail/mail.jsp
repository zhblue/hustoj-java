<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Mail - ${OJ_NAME}</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/katex.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/header.jsp"/>

<div class="container mt-4"><div class="container mt-4">
    <h2>Mail (${newCount} new)</h2>
    <table class="table table-striped">
        <thead>
            <tr><th>From</th><th>Title</th><th>Date</th><th>Status</th></tr>
        </thead>
        <tbody>
            <c:forEach var="m" items="${mails}">
                <tr>
                    <td>${m.fromUser}</td>
                    <td><a href="${pageContext.request.contextPath}/mail?action=view&mid=${m.mailId}">${m.title}</a></td>
                    <td>${m.inDate}</td>
                    <td>${m.newMail ? 'Unread' : 'Read'}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
</body>
</html>
