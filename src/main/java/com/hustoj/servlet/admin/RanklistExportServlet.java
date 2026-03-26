package com.hustoj.servlet.admin;
import com.hustoj.servlet.BaseServlet;

import com.hustoj.db.DB;
import com.hustoj.entity.User;
import com.hustoj.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class RanklistExportServlet extends BaseServlet {
    private final UserService userService = new UserService();

    public void submit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) {
            redirect(req, resp, "/loginpage");
            return;
        }

        resp.setContentType("application/csv");
        resp.setHeader("Content-Disposition", "attachment; filename=ranklist.csv");
        PrintWriter out = resp.getWriter();
        out.println("Rank,UserID,Nick,School,Solved,Submit");

        List<User> users = userService.findAll(0, 1000, "N");
        int rank = 1;
        for (User u : users) {
            out.println(rank++ + "," + u.getUserId() + "," + u.getNick() + "," + u.getSchool() + "," + u.getSolved() + "," + u.getSubmit());
        }
    }
}
