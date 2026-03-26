package com.hustoj.servlet;

import com.hustoj.db.DB;
import com.hustoj.entity.Contest;
import com.hustoj.entity.ContestProblem;
import com.hustoj.entity.Solution;
import com.hustoj.service.ContestService;
import com.hustoj.service.SolutionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class ContestStatisticsServlet extends BaseServlet {

    private final ContestService contestService = new ContestService();
    private final SolutionService solutionService = new SolutionService();

    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int contestId = paramInt(req, "cid", 0);
        if (contestId <= 0) {
            setAttr(req, "error", "Invalid contest ID");
            forward(req, resp, "/WEB-INF/views/error.jsp");
            return;
        }

        Contest contest = contestService.findById(contestId);
        if (contest == null) {
            setAttr(req, "error", "Contest not found");
            forward(req, resp, "/WEB-INF/views/error.jsp");
            return;
        }

        List<ContestProblem> problems = contestService.getContestProblems(contestId);
        List<Solution> solutions = solutionService.findByContestId(contestId, 0, 10000);

        // Count statistics
        int[] acCount = new int[problems.size()];
        int[] submitCount = new int[problems.size()];
        int totalAc = 0;
        int totalSubmit = solutions.size();

        for (Solution s : solutions) {
            int num = s.getNum();
            if (num >= 0 && num < problems.size()) {
                submitCount[num]++;
                if (s.getResult() == 4) {
                    acCount[num]++;
                    totalAc++;
                }
            }
        }

        setAttr(req, "contest", contest);
        setAttr(req, "problems", problems);
        setAttr(req, "acCount", acCount);
        setAttr(req, "submitCount", submitCount);
        setAttr(req, "totalAc", totalAc);
        setAttr(req, "totalSubmit", totalSubmit);
        setAttr(req, "OJ_NAME", DB.getOjName());
        forward(req, resp, "/WEB-INF/views/contest/conteststatistics.jsp");
    }
}
