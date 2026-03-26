package com.hustoj.servlet;

import com.hustoj.db.DB;
import com.hustoj.entity.ShareCode;
import com.hustoj.entity.Solution;
import com.hustoj.service.SolutionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.io.IOException;
import java.util.List;

public class ShareCodeServlet extends BaseServlet {
    private final SolutionService solutionService = new SolutionService();

    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int sid = paramInt(req, "sid", 0);
        if (sid <= 0) {
            setAttr(req, "error", "Solution ID required");
            forward(req, resp, "/WEB-INF/views/error.jsp");
            return;
        }

        if (!DB.isOjAutoShare() && !isAdmin(req)) {
            String userId = getUserId(req);
            Solution s = solutionService.findById(sid);
            if (s == null || !s.getUserId().equals(userId)) {
                setAttr(req, "error", "Access denied");
                forward(req, resp, "/WEB-INF/views/error.jsp");
                return;
            }
        }

        String source = solutionService.getSourceCode(sid);
        Solution sol = solutionService.findById(sid);

        setAttr(req, "source", source);
        setAttr(req, "solution", sol);
        forward(req, resp, "/sharecodepage.jsp");
    }

    public void submit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isLogin(req)) {
            redirect(req, resp, "/loginpage");
            return;
        }

        int sid = paramInt(req, "sid", 0);
        String title = param(req, "title", "Shared Code");
        String userId = getUserId(req);

        Solution sol = solutionService.findById(sid);
        if (sol == null || !sol.getUserId().equals(userId)) {
            setAttr(req, "error", "Access denied");
            forward(req, resp, "/WEB-INF/views/error.jsp");
            return;
        }

        String source = solutionService.getSourceCode(sid);
        String lang = DB.getLanguageName(sol.getLanguage());

        String sql = "INSERT INTO share_code (user_id, title, share_code, language, share_time) VALUES (?, ?, ?, ?, NOW())";
        DB.getJdbcTemplate().update(sql, userId, title, source, lang);

        redirect(req, resp, "/sharecodelist");
    }
}
