<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${news.title}</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/katex.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/header.jsp"/>

<div class="container mt-4"><div class="container mt-4">
    <h2>${news.title}</h2>
    <p class="text-muted">${news.time} by ${news.userId}</p>
    <hr/>
    <div class="content">${news.content}</div>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
</body>
</html>
