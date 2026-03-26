package com.hustoj.servlet;

import com.hustoj.db.DB;
import com.hustoj.entity.Balloon;
import com.hustoj.entity.ContestProblem;
import com.hustoj.service.ContestService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.io.IOException;
import java.util.List;

public class BalloonServlet extends BaseServlet {

    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int cid = paramInt(req, "cid", 0);
        if (cid <= 0) {
            setAttr(req, "error", "Contest ID required");
            forward(req, resp, "/WEB-INF/views/error.jsp");
            return;
        }

        String sql = "SELECT * FROM balloon WHERE cid=? ORDER BY balloon_id ASC";
        List<Balloon> balloons = DB.getJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(Balloon.class), cid);

        List<ContestProblem> problems = new ContestService().getContestProblems(cid);

        setAttr(req, "balloons", balloons);
        setAttr(req, "problems", problems);
        setAttr(req, "cid", cid);
        setAttr(req, "OJ_NAME", DB.getOjName());
        forward(req, resp, "/balloon.jsp");
    }
}
