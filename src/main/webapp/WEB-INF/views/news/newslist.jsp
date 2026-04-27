<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>News</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/katex.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/header.jsp"/>

<div class="container mt-4"><div class="container mt-4">
    <h2>News</h2>
    <c:forEach var="n" items="${newsList}">
        <div class="card">
            <div class="card-header">
                <h3><a href="${pageContext.request.contextPath}/viewnews?id=${n.newsId}">${n.title}</a></h3>
                <small>${n.time} by ${n.userId}</small>
            </div>
            <div class="card-body">
                ${n.content}
            </div>
        </div>
    </c:forEach>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
</body>
</html>
