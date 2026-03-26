package com.hustoj.servlet.admin;
import com.hustoj.servlet.BaseServlet;

import com.hustoj.service.ContestService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ContestPrChangeServlet extends BaseServlet {
    private final ContestService contestService = new ContestService();

    public void submit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) {
            redirect(req, resp, "/loginpage");
            return;
        }
        int contestId = paramInt(req, "contest_id", 0);
        String problemIds = param(req, "problem_ids");
        if (contestId > 0 && problemIds != null) {
            contestService.clearContestProblems(contestId);
            String[] parts = problemIds.split(",");
            int num = 0;
            for (String p : parts) {
                try {
                    int pid = Integer.parseInt(p.trim());
                    contestService.addContestProblem(contestId, pid, "", num++);
                } catch (NumberFormatException e) { /* skip */ }
            }
        }
        redirect(req, resp, "/admin/contest-edit?cid=" + contestId);
    }
}
