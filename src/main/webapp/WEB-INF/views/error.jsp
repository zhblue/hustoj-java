<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error - ${OJ_NAME}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/hoj.css">
</head>
<body>
<div class="container">
    <div class="alert alert-danger">
        <h3>Error</h3>
        <p>${error != null ? error : pageContext.exception.message}</p>
    </div>
    <a href="javascript:history.back()" class="btn btn-default">Go Back</a>
    <a href="${pageContext.request.contextPath}/" class="btn btn-primary">Home</a>
</div>
</body>
</html>
