package com.hustoj.servlet;

import com.hustoj.db.DB;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CategoryServlet extends BaseServlet {
    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sql = "SELECT source, COUNT(*) cnt FROM problem WHERE defunct='N' GROUP BY source HAVING source!='' ORDER BY cnt DESC LIMIT 50";
        List<Map<String, Object>> categories = DB.getJdbcTemplate().queryForList(sql);
        setAttr(req, "categories", categories);
        forward(req, resp, "/category.jsp");
    }
}
