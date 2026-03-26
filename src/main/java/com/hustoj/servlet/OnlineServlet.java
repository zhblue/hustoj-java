package com.hustoj.servlet;

import com.hustoj.db.DB;
import com.hustoj.entity.Online;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.io.IOException;
import java.util.List;

public class OnlineServlet extends BaseServlet {
    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req) && !DB.isOjOnline()) {
            setAttr(req, "error", "Access denied");
            forward(req, resp, "/WEB-INF/views/error.jsp");
            return;
        }
        String sql = "SELECT * FROM online";
        List<Online> onlineList = DB.getJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(Online.class));
        setAttr(req, "onlineList", onlineList);
        setAttr(req, "count", onlineList.size());
        forward(req, resp, "/online.jsp");
    }
}
