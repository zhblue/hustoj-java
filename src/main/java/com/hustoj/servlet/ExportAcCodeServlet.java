package com.hustoj.servlet;

import com.hustoj.db.DB;
import com.hustoj.entity.User;
import com.hustoj.service.SolutionService;
import com.hustoj.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ExportAcCodeServlet extends BaseServlet {
    private final SolutionService solutionService = new SolutionService();
    private final UserService userService = new UserService();

    public void submit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int problemId = paramInt(req, "pid", 0);
        if (problemId <= 0) {
            setAttr(req, "error", "Problem ID required");
            forward(req, resp, "/WEB-INF/views/error.jsp");
            return;
        }

        String sql = "SELECT DISTINCT user_id FROM solution WHERE problem_id=? AND result=4";
        List<String> userIds = DB.getJdbcTemplate().queryForList(sql, String.class, problemId);

        resp.setContentType("application/zip");
        resp.setHeader("Content-Disposition", "attachment; filename=problem_" + problemId + "_ac_codes.zip");

        PrintWriter out = resp.getWriter();
        out.println("UserID,Language,SolutionID");
        for (String uid : userIds) {
            String srcSql = "SELECT solution_id, language FROM solution WHERE problem_id=? AND user_id=? AND result=4 ORDER BY solution_id ASC LIMIT 1";
            try {
                var row = DB.getJdbcTemplate().queryForMap(srcSql, problemId, uid);
                String source = solutionService.getSourceCode(((Number) row.get("solution_id")).intValue());
                out.println(uid + "," + row.get("language") + "," + row.get("solution_id"));
            } catch (Exception e) {
                // skip
            }
        }
    }
}
