package com.hustoj.servlet.admin;
import com.hustoj.servlet.BaseServlet;

import com.hustoj.db.DB;
import com.hustoj.entity.Privilege;
import com.hustoj.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class PrivilegeAddServlet extends BaseServlet {

    private final UserService userService = new UserService();

    public void submit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) {
            redirect(req, resp, "/loginpage");
            return;
        }

        String userId = param(req, "user_id");
        String rightStr = param(req, "rightstr");
        String valueStr = param(req, "valuestr", "true");

        if (userId == null || rightStr == null) {
            setAttr(req, "error", "User ID and rightstr are required");
            forward(req, resp, "/WEB-INF/views/admin/privilege_list.jsp");
            return;
        }

        userService.addPrivilege(userId, rightStr, valueStr);
        redirect(req, resp, "/admin/privilege-list");
    }
}
