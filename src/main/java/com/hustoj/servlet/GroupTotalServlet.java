package com.hustoj.servlet;

import com.hustoj.db.DB;
import com.hustoj.entity.User;
import com.hustoj.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class GroupTotalServlet extends BaseServlet {
    private final UserService userService = new UserService();

    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> groups = userService.getAllGroups();
        setAttr(req, "groups", groups);
        forward(req, resp, "/group_total.jsp");
    }

    public void view(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String groupName = param(req, "group");
        if (groupName == null || groupName.isEmpty()) {
            redirect(req, resp, "/group-total");
            return;
        }
        int page = paramInt(req, "page", 1);
        int limit = 50;
        int offset = (page - 1) * limit;

        List<User> users = userService.findByGroup(groupName, offset, limit);
        int total = userService.countByGroup(groupName);
        int pageCount = (total + limit - 1) / limit;

        setAttr(req, "groupName", groupName);
        setAttr(req, "users", users);
        setAttr(req, "page", page);
        setAttr(req, "pageCount", pageCount);
        forward(req, resp, "/group_total.jsp");
    }
}
