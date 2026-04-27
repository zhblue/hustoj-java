<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">${OJ_NAME}</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto">
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/problemset">Problems</a></li>
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/contest">Contests</a></li>
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/status">Status</a></li>
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/ranklist">Ranklist</a></li>
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/discuss">Discuss</a></li>
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/news">News</a></li>
                <c:if test="${sessionScope.HUSTOJ_user_id != null}">
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/mail">Mail</a></li>
                </c:if>
            </ul>
            <ul class="navbar-nav">
                <c:if test="${sessionScope.HUSTOJ_user_id != null}">
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/userinfo?uid=${sessionScope.HUSTOJ_user_id}">${sessionScope.HUSTOJ_user_id}</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/logout">Logout</a></li>
                </c:if>
                <c:if test="${sessionScope.HUSTOJ_user_id == null}">
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/loginpage">Login</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/registerpage">Register</a></li>
                </c:if>
                <c:if test="${sessionScope.HUSTOJ_administrator != null}">
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/admin/">Admin</a></li>
                </c:if>
            </ul>
        </div>
    </div>
</nav>
