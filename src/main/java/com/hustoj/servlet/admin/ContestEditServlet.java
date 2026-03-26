package com.hustoj.servlet.admin;
import com.hustoj.servlet.BaseServlet;

import com.hustoj.db.DB;
import com.hustoj.entity.Contest;
import com.hustoj.entity.ContestProblem;
import com.hustoj.service.ContestService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

public class ContestEditServlet extends BaseServlet {

    private final ContestService contestService = new ContestService();

    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) {
            redirect(req, resp, "/loginpage");
            return;
        }

        int contestId = paramInt(req, "cid", 0);
        Contest contest = contestService.findById(contestId);
        if (contest == null) {
            setAttr(req, "error", "Contest not found");
            forward(req, resp, "/WEB-INF/views/error.jsp");
            return;
        }

        List<ContestProblem> problems = contestService.getContestProblems(contestId);

        setAttr(req, "contest", contest);
        setAttr(req, "problems", problems);
        setAttr(req, "OJ_NAME", DB.getOjName());
        forward(req, resp, "/WEB-INF/views/admin/contest_edit.jsp");
    }

    public void submit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) {
            redirect(req, resp, "/loginpage");
            return;
        }

        int contestId = paramInt(req, "contest_id", 0);
        Contest c = contestService.findById(contestId);
        if (c == null) {
            setAttr(req, "error", "Contest not found");
            forward(req, resp, "/WEB-INF/views/error.jsp");
            return;
        }

        String title = param(req, "title");
        String startTime = param(req, "start_time");
        String endTime = param(req, "end_time");
        String description = param(req, "description");
        int private_ = paramInt(req, "private", 0);
        String password = param(req, "password");
        int contestType = paramInt(req, "contest_type", 0);
        String subnet = param(req, "subnet");
        int langMask = paramInt(req, "langmask", 0);
        String defunct = param(req, "defunct", "N");

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            c.setTitle(title);
            c.setStartTime(new Timestamp(sdf.parse(startTime).getTime()));
            c.setEndTime(new Timestamp(sdf.parse(endTime).getTime()));
            c.setDescription(description != null ? description : "");
            c.setPrivate_(private_);
            c.setPassword(password != null ? password : "");
            c.setContestType(contestType);
            c.setSubnet(subnet != null ? subnet : "");
            c.setLangMask(langMask);
            c.setDefunct(defunct);

            contestService.update(c);

            setAttr(req, "message", "Contest updated");
            setAttr(req, "contest", c);
        } catch (Exception e) {
            setAttr(req, "error", "Invalid time format");
        }

        List<ContestProblem> problems = contestService.getContestProblems(contestId);
        setAttr(req, "problems", problems);
        forward(req, resp, "/WEB-INF/views/admin/contest_edit.jsp");
    }
}
