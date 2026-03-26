package com.hustoj.servlet.admin;
import com.hustoj.servlet.BaseServlet;

import com.hustoj.db.DB;
import com.hustoj.entity.Solution;
import com.hustoj.service.SolutionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;

public class SuspectListServlet extends BaseServlet {
    private final SolutionService solutionService = new SolutionService();

    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) {
            redirect(req, resp, "/loginpage");
            return;
        }

        // Find users with too many submissions (potential bot behavior)
        String sql = "SELECT user_id, COUNT(*) cnt FROM solution WHERE result!=4 GROUP BY user_id HAVING cnt>100 ORDER BY cnt DESC LIMIT 100";
        List<Map<String, Object>> suspects = DB.getJdbcTemplate().queryForList(sql);

        setAttr(req, "suspects", suspects);
        setAttr(req, "OJ_NAME", DB.getOjName());
        forward(req, resp, "/WEB-INF/views/admin/suspect_list.jsp");
    }
}
