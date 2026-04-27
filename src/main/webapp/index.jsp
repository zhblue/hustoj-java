<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${OJ_NAME} - Home</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/katex.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/header.jsp"/>
<div class="container mt-4">
    <div class="p-5 mb-4 bg-light rounded-3">
        <h1 class="display-4">Welcome to ${OJ_NAME}</h1>
        <p class="lead">Online Judge System</p>
    </div>
    <div class="row">
        <div class="col-md-4">
            <div class="card border-primary">
                <div class="card-header bg-primary text-white">Problems</div>
                <div class="card-body">
                    <a href="${pageContext.request.contextPath}/problemset">View Problem Set</a>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card border-success">
                <div class="card-header bg-success text-white">Contests</div>
                <div class="card-body">
                    <a href="${pageContext.request.contextPath}/contest">View Contests</a>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card border-info">
                <div class="card-header bg-info text-white">Status</div>
                <div class="card-body">
                    <a href="${pageContext.request.contextPath}/status">View Status</a>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
</body>
</html>
