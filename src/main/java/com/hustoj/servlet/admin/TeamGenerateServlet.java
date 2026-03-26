package com.hustoj.servlet.admin;
import com.hustoj.servlet.BaseServlet;

import com.hustoj.db.DB;
import com.hustoj.entity.User;
import com.hustoj.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class TeamGenerateServlet extends BaseServlet {
    private final UserService userService = new UserService();

    public void submit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) {
            redirect(req, resp, "/loginpage");
            return;
        }
        String groupName = param(req, "group_name");
        int count = paramInt(req, "count", 10);
        // Generate team members with random user_ids
        int maxId = userService.count(null);
        setAttr(req, "groupName", groupName);
        setAttr(req, "count", count);
        forward(req, resp, "/WEB-INF/views/admin/team_generate.jsp");
    }
}
