<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>500 Internal Error</title>
</head>
<body>
<div class="container">
    <h1>500 - Internal Server Error</h1>
    <p>${pageContext.exception.message}</p>
    <a href="/">Go Home</a>
</div>
</body>
</html>
