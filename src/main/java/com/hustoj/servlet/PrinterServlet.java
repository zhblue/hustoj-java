package com.hustoj.servlet;

import com.hustoj.db.DB;
import com.hustoj.entity.Printer;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.io.IOException;
import java.util.List;

public class PrinterServlet extends BaseServlet {

    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isLogin(req)) {
            redirect(req, resp, "/loginpage");
            return;
        }
        String userId = getUserId(req);
        String sql = "SELECT * FROM printer WHERE user_id=? ORDER BY printer_id DESC LIMIT 50";
        List<Printer> printers = DB.getJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(Printer.class), userId);
        setAttr(req, "printers", printers);
        forward(req, resp, "/printer.jsp");
    }

    public void submit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isLogin(req)) {
            redirect(req, resp, "/loginpage");
            return;
        }
        String userId = getUserId(req);
        String content = param(req, "content");
        if (content != null && !content.isEmpty()) {
            String sql = "INSERT INTO printer (user_id, content, in_date, status) VALUES (?, ?, NOW(), 0)";
            DB.getJdbcTemplate().update(sql, userId, content);
        }
        redirect(req, resp, "/printer");
    }
}
