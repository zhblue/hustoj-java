package com.hustoj.servlet;

import com.hustoj.db.DB;
import com.hustoj.entity.Solution;
import com.hustoj.service.SolutionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class StatusServlet extends BaseServlet {

    private final SolutionService solutionService = new SolutionService();

    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        boolean ajax = path.endsWith("ajax") || paramBool(req, "ajax");

        if (ajax) {
            renderAjax(req, resp);
            return;
        }

        int page = paramInt(req, "page", 1);
        int limit = 50;
        int offset = (page - 1) * limit;

        String userId = getUserId(req);
        int problemId = paramInt(req, "problem_id", 0);
        int result = paramInt(req, "result", -1);
        int language = paramInt(req, "language", -1);

        List<Solution> solutions;
        if (isAdmin(req) || DB.isOjPublicStatus()) {
            if (userId != null && !userId.isEmpty() && problemId > 0) {
                solutions = solutionService.search(userId, problemId, result, language, null, offset, limit);
            } else if (userId != null && !userId.isEmpty()) {
                solutions = solutionService.findByUserId(userId, offset, limit);
            } else if (problemId > 0) {
                solutions = solutionService.findByProblemId(problemId, offset, limit);
            } else {
                solutions = solutionService.findAll(offset, limit);
            }
        } else {
            // Non-admin can only see own submissions
            userId = getUserId(req);
            if (userId == null) {
                redirect(req, resp, "/loginpage");
                return;
            }
            solutions = solutionService.findByUserId(userId, offset, limit);
        }

        int total = solutionService.count();
        int pageCount = (total + limit - 1) / limit;

        setAttr(req, "solutions", solutions);
        setAttr(req, "page", page);
        setAttr(req, "pageCount", pageCount);
        setAttr(req, "total", total);
        setAttr(req, "OJ_NAME", DB.getOjName());
        setAttr(req, "OJ_SIM", DB.isOjSim());
        forward(req, resp, "/WEB-INF/views/status/status.jsp");
    }

    private void renderAjax(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        int limit = paramInt(req, "limit", 50);
        int offset = paramInt(req, "offset", 0);
        int cid = paramInt(req, "cid", 0);

        List<Solution> solutions = cid > 0
            ? solutionService.findByContestId(cid, offset, limit)
            : solutionService.findAll(offset, limit);

        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < solutions.size(); i++) {
            Solution s = solutions.get(i);
            if (i > 0) json.append(",");
            json.append("{");
            json.append("\"solution_id\":").append(s.getSolutionId()).append(",");
            json.append("\"problem_id\":").append(s.getProblemId()).append(",");
            json.append("\"user_id\":\"").append(escapeJson(s.getUserId())).append("\",");
            json.append("\"result\":").append(s.getResult()).append(",");
            json.append("\"language\":").append(s.getLanguage()).append(",");
            json.append("\"time\":").append(s.getTime()).append(",");
            json.append("\"memory\":").append(s.getMemory()).append(",");
            json.append("\"code_length\":").append(s.getCodeLength()).append(",");
            json.append("\"in_date\":\"").append(s.getInDate()).append("\"");
            json.append("}");
        }
        json.append("]");
        out.print(json.toString());
    }

    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
