package com.hustoj.servlet;

import com.hustoj.db.DB;
import com.hustoj.service.UserService;
import com.hustoj.util.IpUtil;
import com.hustoj.util.SecurityUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class RegisterServlet extends BaseServlet {

    private final UserService userService = new UserService();

    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!DB.isOjRegister()) {
            setAttr(req, "error", "Registration is disabled");
            forward(req, resp, "/WEB-INF/views/error.jsp");
            return;
        }
        HttpSession session = req.getSession(true);
        setAttr(req, "csrfToken", getCsrfToken(session));
        setAttr(req, "OJ_NAME", DB.getOjName());
        forward(req, resp, "/WEB-INF/views/user/register.jsp");
    }

    public void submit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!DB.isOjRegister()) {
            setAttr(req, "error", "Registration is disabled");
            forward(req, resp, "/WEB-INF/views/error.jsp");
            return;
        }

        String userId = param(req, "user_id");
        String password = param(req, "password");
        String rpPassword = param(req, "rptpassword");
        String email = param(req, "email");
        String nick = param(req, "nick");
        String school = param(req, "school");

        // Validate
        if (userId == null || password == null || email == null) {
            setAttr(req, "error", "All fields are required");
            forward(req, resp, "/WEB-INF/views/user/register.jsp");
            return;
        }

        if (!password.equals(rpPassword)) {
            setAttr(req, "error", "Passwords do not match");
            forward(req, resp, "/WEB-INF/views/user/register.jsp");
            return;
        }

        if (!SecurityUtil.isValidUserName(userId)) {
            setAttr(req, "error", "Invalid username format");
            forward(req, resp, "/WEB-INF/views/user/register.jsp");
            return;
        }

        if (userService.exists(userId)) {
            setAttr(req, "error", "Username already exists");
            forward(req, resp, "/WEB-INF/views/user/register.jsp");
            return;
        }

        if (userService.emailExists(email)) {
            setAttr(req, "error", "Email already registered");
            forward(req, resp, "/WEB-INF/views/user/register.jsp");
            return;
        }

        if (!SecurityUtil.isPasswordStrongEnough(password)) {
            setAttr(req, "error", "Password is too simple, need at least 8 chars with letters and numbers");
            forward(req, resp, "/WEB-INF/views/user/register.jsp");
            return;
        }

        String ip = IpUtil.getClientIp(req);
        int result = userService.register(userId, password, email, nick != null ? nick : "", school != null ? school : "", ip);

        if (result > 0) {
            redirect(req, resp, "/loginpage?registered=1");
        } else if (result == -1) {
            setAttr(req, "error", "Username already exists");
            forward(req, resp, "/WEB-INF/views/user/register.jsp");
        } else if (result == -2) {
            setAttr(req, "error", "Email already registered");
            forward(req, resp, "/WEB-INF/views/user/register.jsp");
        } else {
            setAttr(req, "error", "Registration failed");
            forward(req, resp, "/WEB-INF/views/user/register.jsp");
        }
    }
}
