package com.hustoj.servlet;

import com.hustoj.db.DB;
import com.hustoj.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GroupStatisticsServlet extends BaseServlet {
    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sql = "SELECT group_name, COUNT(*) users, SUM(solved) total_solved, SUM(submit) total_submit " +
                     "FROM users WHERE group_name!='' GROUP BY group_name ORDER BY total_solved DESC";
        List<Map<String, Object>> stats = DB.getJdbcTemplate().queryForList(sql);
        setAttr(req, "stats", stats);
        forward(req, resp, "/group_statistics.jsp");
    }
}
