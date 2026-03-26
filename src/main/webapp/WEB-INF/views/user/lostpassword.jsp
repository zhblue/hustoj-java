<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lost Password</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/hoj.css">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="panel panel-default">
                <div class="panel-heading"><h3>Lost Password</h3></div>
                <div class="panel-body">
                    <c:if test="${error != null}"><div class="alert alert-danger">${error}</div></c:if>
                    <c:if test="${message != null}"><div class="alert alert-success">${message}</div></c:if>
                    <form method="post">
                        <div class="form-group">
                            <label>Username</label>
                            <input type="text" name="user_id" class="form-control" required/>
                        </div>
                        <div class="form-group">
                            <label>Email</label>
                            <input type="email" name="email" class="form-control" required/>
                        </div>
                        <button type="submit" class="btn btn-primary">Send Reset Link</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
