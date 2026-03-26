package com.hustoj.servlet;

import com.hustoj.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class LostPasswordServlet extends BaseServlet {

    private final UserService userService = new UserService();

    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forward(req, resp, "/WEB-INF/views/user/lostpassword.jsp");
    }

    public void submit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = param(req, "user_id");
        String email = param(req, "email");

        if (userId == null || email == null || userId.isEmpty() || email.isEmpty()) {
            setAttr(req, "error", "Username and email are required");
            forward(req, resp, "/WEB-INF/views/user/lostpassword.jsp");
            return;
        }

        userService.findById(userId);
        setAttr(req, "message", "If the username and email match, a reset link will be sent");
        forward(req, resp, "/WEB-INF/views/user/lostpassword.jsp");
    }
}
