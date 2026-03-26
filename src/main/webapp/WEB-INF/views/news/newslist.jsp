<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>News</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/hoj.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/header.jsp"/>
<div class="container">
    <h2>News</h2>
    <c:forEach var="n" items="${newsList}">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3><a href="${pageContext.request.contextPath}/viewnews?id=${n.newsId}">${n.title}</a></h3>
                <small>${n.time} by ${n.userId}</small>
            </div>
            <div class="panel-body">
                ${n.content}
            </div>
        </div>
    </c:forEach>
</div>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
</body>
</html>
