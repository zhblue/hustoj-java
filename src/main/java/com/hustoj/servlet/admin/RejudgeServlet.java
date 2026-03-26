package com.hustoj.servlet.admin;

import com.hustoj.servlet.BaseServlet;
import com.hustoj.service.SolutionService;
import com.hustoj.util.JudgeUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class RejudgeServlet extends BaseServlet {

    private final SolutionService solutionService = new SolutionService();

    public void submit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) {
            setAttr(req, "error", "Admin only");
            forward(req, resp, "/WEB-INF/views/error.jsp");
            return;
        }

        String solutionIds = param(req, "solution_id");
        String problemId = param(req, "problem_id");
        String contestId = param(req, "contest_id");

        int count = 0;

        if (solutionIds != null && !solutionIds.isEmpty()) {
            String[] ids = solutionIds.split(",");
            for (String sid : ids) {
                try {
                    int id = Integer.parseInt(sid.trim());
                    solutionService.rejudge(id);
                    JudgeUtil.triggerJudge(id);
                    count++;
                } catch (NumberFormatException e) {
                    // skip
                }
            }
        } else if (problemId != null && !problemId.isEmpty()) {
            try {
                int pid = Integer.parseInt(problemId);
                solutionService.rejudgeByProblemId(pid);
                count = -1; // indicate bulk
            } catch (NumberFormatException e) {
                // skip
            }
        } else if (contestId != null && !contestId.isEmpty()) {
            try {
                int cid = Integer.parseInt(contestId);
                solutionService.rejudgeByContestId(cid);
                count = -1; // indicate bulk
            } catch (NumberFormatException e) {
                // skip
            }
        }

        setAttr(req, "count", count);
        setAttr(req, "message", count < 0 ? "Bulk rejudge triggered" : count + " solutions rejudged");
        forward(req, resp, "/WEB-INF/views/admin/rejudge.jsp");
    }
}
