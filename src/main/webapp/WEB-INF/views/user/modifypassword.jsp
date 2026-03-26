<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Change Password</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/hoj.css">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="panel panel-default">
                <div class="panel-heading"><h3>Change Password</h3></div>
                <div class="panel-body">
                    <c:if test="${error != null}"><div class="alert alert-danger">${error}</div></c:if>
                    <c:if test="${message != null}"><div class="alert alert-success">${message}</div></c:if>
                    <form method="post">
                        <div class="form-group">
                            <label>Old Password</label>
                            <input type="password" name="old_password" class="form-control" required/>
                        </div>
                        <div class="form-group">
                            <label>New Password</label>
                            <input type="password" name="password" class="form-control" required/>
                        </div>
                        <div class="form-group">
                            <label>Repeat Password</label>
                            <input type="password" name="rptpassword" class="form-control" required/>
                        </div>
                        <button type="submit" class="btn btn-primary">Change</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
