package com.hustoj.servlet.admin;
import com.hustoj.servlet.BaseServlet;

import com.hustoj.db.DB;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

public class SolutionStatisticsServlet extends BaseServlet {
    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) {
            redirect(req, resp, "/loginpage");
            return;
        }

        int total = DB.getJdbcTemplate().queryForObject("SELECT COUNT(*) FROM solution", Integer.class);
        int pending = DB.getJdbcTemplate().queryForObject("SELECT COUNT(*) FROM solution WHERE result<4", Integer.class);
        int ac = DB.getJdbcTemplate().queryForObject("SELECT COUNT(*) FROM solution WHERE result=4", Integer.class);
        int ce = DB.getJdbcTemplate().queryForObject("SELECT COUNT(*) FROM solution WHERE result=11", Integer.class);
        int wa = DB.getJdbcTemplate().queryForObject("SELECT COUNT(*) FROM solution WHERE result=6", Integer.class);

        setAttr(req, "total", total);
        setAttr(req, "pending", pending);
        setAttr(req, "ac", ac);
        setAttr(req, "ce", ce);
        setAttr(req, "wa", wa);
        setAttr(req, "OJ_NAME", DB.getOjName());
        forward(req, resp, "/WEB-INF/views/admin/solution_statistics.jsp");
    }
}
