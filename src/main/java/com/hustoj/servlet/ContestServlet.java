package com.hustoj.servlet;

import com.hustoj.db.DB;
import com.hustoj.entity.Contest;
import com.hustoj.entity.ContestProblem;
import com.hustoj.entity.Solution;
import com.hustoj.entity.User;
import com.hustoj.service.ContestService;
import com.hustoj.service.SolutionService;
import com.hustoj.service.UserService;
import com.hustoj.util.IpUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class ContestServlet extends BaseServlet {

    private final ContestService contestService = new ContestService();
    private final SolutionService solutionService = new SolutionService();
    private final UserService userService = new UserService();

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
        String userId = getUserId(req);

        // Check private contest access
        if (contest.getPrivate_() == 1 && !isAdmin(req)) {
            if (userId == null) {
                redirect(req, resp, "/loginpage");
                return;
            }
            // Password check would go here
        }

        setAttr(req, "contest", contest);
        setAttr(req, "problems", problems);
        setAttr(req, "OJ_NAME", DB.getOjName());
        forward(req, resp, "/WEB-INF/views/contest/contest.jsp");
    }

    public void problem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int contestId = paramInt(req, "cid", 0);
        int num = paramInt(req, "pid", 0);
        if (contestId <= 0 || num < 0) {
            setAttr(req, "error", "Invalid parameters");
            forward(req, resp, "/WEB-INF/views/error.jsp");
            return;
        }

        Contest contest = contestService.findById(contestId);
        if (contest == null) {
            setAttr(req, "error", "Contest not found");
            forward(req, resp, "/WEB-INF/views/error.jsp");
            return;
        }

        ContestProblem cp = contestService.getContestProblem(contestId, num);
        if (cp == null) {
            setAttr(req, "error", "Problem not found in contest");
            forward(req, resp, "/WEB-INF/views/error.jsp");
            return;
        }

        setAttr(req, "contest", contest);
        setAttr(req, "contestProblem", cp);
        setAttr(req, "OJ_NAME", DB.getOjName());
        forward(req, resp, "/WEB-INF/views/contest/contest-problem.jsp");
    }
}
