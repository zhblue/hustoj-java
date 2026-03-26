package com.hustoj.servlet.admin;
import com.hustoj.servlet.BaseServlet;

import com.hustoj.db.DB;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BackupServlet extends BaseServlet {
    public void submit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) {
            redirect(req, resp, "/loginpage");
            return;
        }
        resp.setContentType("application/octet-stream");
        String filename = "hustoj_backup_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".sql";
        resp.setHeader("Content-Disposition", "attachment; filename=" + filename);
        PrintWriter out = resp.getWriter();
        out.println("-- HUSTOJ Backup");
        out.println("-- Generated: " + new Date());
        out.println("SET NAMES utf8mb4;");
        setAttr(req, "message", "Backup generated");
        forward(req, resp, "/WEB-INF/views/admin/backup.jsp");
    }
}
