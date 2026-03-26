package com.hustoj.servlet;

import com.hustoj.db.DB;
import com.hustoj.entity.Problem;
import com.hustoj.entity.Solution;
import com.hustoj.entity.User;
import com.hustoj.service.ProblemService;
import com.hustoj.service.SolutionService;
import com.hustoj.service.UserService;
import com.hustoj.util.IpUtil;
import com.hustoj.util.JudgeUtil;
import com.hustoj.util.SecurityUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class SubmitServlet extends BaseServlet {

    private final SolutionService solutionService = new SolutionService();
    private final ProblemService problemService = new ProblemService();
    private final UserService userService = new UserService();

    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int problemId = paramInt(req, "id", 0);
        if (problemId <= 0) {
            setAttr(req, "error", "Invalid problem ID");
            forward(req, resp, "/WEB-INF/views/error.jsp");
            return;
        }

        Problem problem = problemService.findByIdNotDefunct(problemId);
        if (problem == null) {
            setAttr(req, "error", "Problem not found");
            forward(req, resp, "/WEB-INF/views/error.jsp");
            return;
        }

        setAttr(req, "problem", problem);
        setAttr(req, "OJ_LANG", DB.getOjLang());
        forward(req, resp, "/WEB-INF/views/problem/submitpage.jsp");
    }

    public void submit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = getUserId(req);
        if (userId == null) {
            redirect(req, resp, "/loginpage");
            return;
        }

        String ip = IpUtil.getClientIp(req);
        HttpSession session = req.getSession(false);

        // VCODE check
        if (DB.isOjVcode()) {
            String vcode = param(req, "vcode");
            String sessionVcode = session != null ? sessionAttr(session, DB.SESSION_VCODE) : null;
            if (sessionVcode == null || !sessionVcode.equalsIgnoreCase(vcode)) {
                setAttr(req, "error", "Verification code wrong");
                forward(req, resp, "/WEB-INF/views/error.jsp");
                return;
            }
            if (session != null) session.removeAttribute(DB.SESSION_VCODE);
        }

        // Check pending submissions
        int pendingCount = solutionService.countPending();
        if (pendingCount > 50) {
            DB.getConfig("oj.vcode", "true"); // force vcode next time
        }

        // Get parameters
        int problemId = paramInt(req, "id", 0);
        int language = paramInt(req, "language", 0);
        String source = param(req, "source");
        int cid = paramInt(req, "cid", 0);
        int pid = paramInt(req, "pid", -1);

        // Validate
        if (problemId <= 0 || source == null || source.isEmpty()) {
            setAttr(req, "error", "Missing required parameters");
            forward(req, resp, "/WEB-INF/views/error.jsp");
            return;
        }

        // Check contest context
        if (cid > 0) {
            // Contest submission - validate
            // Problem id comes from contest_problem num
        }

        // Create solution
        Solution solution = new Solution();
        solution.setProblemId(problemId);
        solution.setUserId(userId);
        solution.setNick("");
        solution.setTime(0);
        solution.setMemory(0);
        solution.setResult(0); // Pending
        solution.setLanguage(language);
        solution.setIp(ip);
        solution.setContestId(cid);
        solution.setValid(1);
        solution.setNum(pid);
        solution.setCodeLength(source.length());
        solution.setPassRate(0);
        solution.setRemoteOj("");
        solution.setRemoteId("");

        int solutionId = solutionService.insert(solution);

        // Save source code
        solutionService.saveSourceCode(solutionId, source);

        // Increment user submit count
        userService.incrementSubmit(userId);

        // Increment problem submit count
        problemService.incrementSubmit(problemId);

        // Trigger judge via UDP
        JudgeUtil.triggerJudge(solutionId);

        // Redirect to status
        if (cid > 0) {
            redirect(req, resp, "/status?cid=" + cid);
        } else {
            redirect(req, resp, "/status");
        }
    }
}
