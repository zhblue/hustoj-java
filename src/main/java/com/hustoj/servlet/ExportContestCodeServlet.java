package com.hustoj.servlet;

import com.hustoj.db.DB;
import com.hustoj.service.SolutionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class ExportContestCodeServlet extends BaseServlet {
    private final SolutionService solutionService = new SolutionService();

    public void submit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int contestId = paramInt(req, "cid", 0);
        if (contestId <= 0) {
            setAttr(req, "error", "Contest ID required");
            forward(req, resp, "/WEB-INF/views/error.jsp");
            return;
        }

        String sql = "SELECT solution_id, user_id, language FROM solution WHERE contest_id=? ORDER BY user_id, solution_id";
        List<Map<String, Object>> solutions = DB.getJdbcTemplate().queryForList(sql, contestId);

        resp.setContentType("text/csv");
        resp.setHeader("Content-Disposition", "attachment; filename=contest_" + contestId + "_codes.csv");

        PrintWriter out = resp.getWriter();
        out.println("SolutionID,UserID,Language");
        for (Map<String, Object> sol : solutions) {
            out.println(sol.get("solution_id") + "," + sol.get("user_id") + "," + sol.get("language"));
        }
    }
}
