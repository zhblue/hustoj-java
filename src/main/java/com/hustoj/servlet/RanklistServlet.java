package com.hustoj.servlet;

import com.hustoj.db.DB;
import com.hustoj.entity.User;
import com.hustoj.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class RanklistServlet extends BaseServlet {

    private final UserService userService = new UserService();

    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = paramInt(req, "page", 1);
        int limit = 100;
        int offset = (page - 1) * limit;

        List<User> users = userService.findAll(offset, limit, "N");
        int total = userService.count("N");
        int acUsers = userService.countAcUser();

        int pageCount = (total + limit - 1) / limit;

        setAttr(req, "users", users);
        setAttr(req, "page", page);
        setAttr(req, "pageCount", pageCount);
        setAttr(req, "total", total);
        setAttr(req, "acUsers", acUsers);
        setAttr(req, "OJ_NAME", DB.getOjName());
        forward(req, resp, "/ranklist.jsp");
    }
}
