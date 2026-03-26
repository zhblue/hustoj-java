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

public class ContestAddServlet extends BaseServlet {

    private final ContestService contestService = new ContestService();

    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) {
            redirect(req, resp, "/loginpage");
            return;
        }
        setAttr(req, "OJ_NAME", DB.getOjName());
        forward(req, resp, "/WEB-INF/views/admin/contest_add.jsp");
    }

    public void submit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) {
            redirect(req, resp, "/loginpage");
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
        String userId = getUserId(req);

        if (title == null || startTime == null || endTime == null) {
            setAttr(req, "error", "Title and time are required");
            forward(req, resp, "/WEB-INF/views/admin/contest_add.jsp");
            return;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            long start = sdf.parse(startTime).getTime();
            long end = sdf.parse(endTime).getTime();

            Contest c = new Contest();
            c.setTitle(title);
            c.setStartTime(new Timestamp(start));
            c.setEndTime(new Timestamp(end));
            c.setDescription(description != null ? description : "");
            c.setPrivate_(private_);
            c.setPassword(password != null ? password : "");
            c.setContestType(contestType);
            c.setSubnet(subnet != null ? subnet : "");
            c.setLangMask(langMask);
            c.setUserId(userId);
            c.setDefunct("N");

            int contestId = contestService.insert(c);

            // Add problems
            String[] problemIds = req.getParameterValues("problem_id");
            if (problemIds != null) {
                int num = 0;
                for (String pid : problemIds) {
                    try {
                        int problemId = Integer.parseInt(pid);
                        contestService.addContestProblem(contestId, problemId, "", num++);
                    } catch (NumberFormatException e) {
                        // skip
                    }
                }
            }

            redirect(req, resp, "/admin/contest-edit?cid=" + contestId);
        } catch (Exception e) {
            setAttr(req, "error", "Invalid time format");
            forward(req, resp, "/WEB-INF/views/admin/contest_add.jsp");
        }
    }
}
