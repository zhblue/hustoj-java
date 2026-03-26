package com.hustoj.servlet.admin;
import com.hustoj.servlet.BaseServlet;

import com.hustoj.db.DB;
import com.hustoj.entity.Privilege;
import com.hustoj.entity.User;
import com.hustoj.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class PrivilegeListServlet extends BaseServlet {

    private final UserService userService = new UserService();

    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) {
            redirect(req, resp, "/loginpage");
            return;
        }

        String userId = param(req, "user_id");
        List<Privilege> privileges;
        List<User> users = userService.findAll(0, 1000, null);

        if (userId != null && !userId.isEmpty()) {
            privileges = userService.getPrivileges(userId);
        } else {
            privileges = List.of();
        }

        setAttr(req, "privileges", privileges);
        setAttr(req, "users", users);
        setAttr(req, "OJ_NAME", DB.getOjName());
        forward(req, resp, "/WEB-INF/views/admin/privilege_list.jsp");
    }
}
