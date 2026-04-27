<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - ${OJ_NAME}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-6 offset-md-3">
            <div class="card">
                <div class="card-header">
                    <h3>Login to ${OJ_NAME}</h3>
                </div>
                <div class="card-body">
                    <c:if test="${error != null}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>
                    <c:if test="${param.registered == '1'}">
                        <div class="alert alert-success">Registration successful, please login.</div>
                    </c:if>
                    <form method="post" action="${pageContext.request.contextPath}/login">
                        <input type="hidden" name="action" value="submit"/>
                        <input type="hidden" name="_token" value="${csrfToken}"/>
                        <div class="mb-3">
                            <label>Username</label>
                            <input type="text" name="user_id" class="form-control" required/>
                        </div>
                        <div class="mb-3">
                            <label>Password</label>
                            <input type="password" name="password" class="form-control" required/>
                        </div>
                        <c:if test="${OJ_VCODE}">
                            <div class="mb-3">
                                <label>Verification Code</label>
                                <input type="text" name="vcode" class="form-control"/>
                            </div>
                        </c:if>
                        <div class="mb-3">
                            <button type="submit" class="btn btn-primary">Login</button>
                            <a href="${pageContext.request.contextPath}/registerpage" class="btn btn-link">Register</a>
                            <a href="${pageContext.request.contextPath}/lostpassword" class="btn btn-link">Lost Password</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
