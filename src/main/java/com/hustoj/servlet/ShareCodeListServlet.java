package com.hustoj.servlet;

import com.hustoj.db.DB;
import com.hustoj.entity.ShareCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.io.IOException;
import java.util.List;

public class ShareCodeListServlet extends BaseServlet {
    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = paramInt(req, "page", 1);
        int limit = 50;
        int offset = (page - 1) * limit;

        String sql = "SELECT * FROM share_code ORDER BY share_id DESC LIMIT ? OFFSET ?";
        List<ShareCode> codes = DB.getJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(ShareCode.class), limit, offset);

        int total = DB.getJdbcTemplate().queryForObject("SELECT COUNT(*) FROM share_code", Integer.class);
        int pageCount = (total + limit - 1) / limit;

        setAttr(req, "codes", codes);
        setAttr(req, "page", page);
        setAttr(req, "pageCount", pageCount);
        forward(req, resp, "/sharecodelist.jsp");
    }
}
