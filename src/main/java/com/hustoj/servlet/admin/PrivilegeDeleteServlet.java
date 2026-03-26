package com.hustoj.servlet.admin;
import com.hustoj.servlet.BaseServlet;

import com.hustoj.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class PrivilegeDeleteServlet extends BaseServlet {

    private final UserService userService = new UserService();

    public void submit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) {
            redirect(req, resp, "/loginpage");
            return;
        }

        String userId = param(req, "user_id");
        String rightStr = param(req, "rightstr");

        if (userId != null && rightStr != null) {
            userService.deletePrivilege(userId, rightStr);
        }
        redirect(req, resp, "/admin/privilege-list");
    }
}
