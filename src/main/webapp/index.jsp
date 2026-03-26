<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${OJ_NAME} - Home</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/hoj.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/header.jsp"/>
<div class="container">
    <div class="hero">
        <h1>Welcome to ${OJ_NAME}</h1>
        <p>Online Judge System</p>
    </div>
    <div class="row">
        <div class="col-md-4">
            <div class="panel panel-primary">
                <div class="panel-heading">Problems</div>
                <div class="panel-body">
                    <a href="${pageContext.request.contextPath}/problemset">View Problem Set</a>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="panel panel-success">
                <div class="panel-heading">Contests</div>
                <div class="panel-body">
                    <a href="${pageContext.request.contextPath}/contest">View Contests</a>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="panel panel-info">
                <div class="panel-heading">Status</div>
                <div class="panel-body">
                    <a href="${pageContext.request.contextPath}/status">View Status</a>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
</body>
</html>
