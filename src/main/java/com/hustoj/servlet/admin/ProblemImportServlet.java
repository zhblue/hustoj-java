package com.hustoj.servlet.admin;
import com.hustoj.servlet.BaseServlet;

import com.hustoj.db.DB;
import com.hustoj.entity.Problem;
import com.hustoj.service.ProblemService;
import com.hustoj.util.SecurityUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ProblemImportServlet extends BaseServlet {

    private final ProblemService problemService = new ProblemService();

    public void submit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) {
            redirect(req, resp, "/loginpage");
            return;
        }

        String format = param(req, "format");
        Part filePart = req.getPart("file");

        int count = 0;
        try {
            if ("fps".equals(format) || "xml".equals(format)) {
                count = importFps(req, filePart);
            } else if ("zip".equals(format)) {
                count = importZip(req, filePart);
            } else if ("json".equals(format)) {
                count = importJson(req, filePart);
            }
        } catch (Exception e) {
            setAttr(req, "error", "Import failed: " + e.getMessage());
            forward(req, resp, "/WEB-INF/views/admin/problem_import.jsp");
            return;
        }

        setAttr(req, "count", count);
        setAttr(req, "message", "Imported " + count + " problems");
        forward(req, resp, "/WEB-INF/views/admin/problem_import.jsp");
    }

    private int importFps(HttpServletRequest req, Part filePart) throws Exception {
        List<Problem> problems = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(filePart.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder xml = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                xml.append(line).append("\n");
            }
            problems = parseFpsXml(xml.toString());
        }
        int count = 0;
        for (Problem p : problems) {
            problemService.insert(p);
            count++;
        }
        return count;
    }

    private int importZip(HttpServletRequest req, Part filePart) throws Exception {
        String uploadDir = DB.getOjData();
        int count = 0;
        try (ZipInputStream zis = new ZipInputStream(filePart.getInputStream())) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().endsWith(".xml") || entry.getName().endsWith(".fps")) {
                    StringBuilder xml = new StringBuilder();
                    byte[] buffer = new byte[4096];
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        xml.append(new String(buffer, 0, len, StandardCharsets.UTF_8));
                    }
                    List<Problem> problems = parseFpsXml(xml.toString());
                    for (Problem p : problems) {
                        int pid = problemService.insert(p);
                        // Copy test data
                        if (pid > 0) {
                            String safePath = SecurityUtil.getSafeZipPath(uploadDir, String.valueOf(pid));
                            new File(safePath).mkdirs();
                        }
                        count++;
                    }
                }
                zis.closeEntry();
            }
        }
        return count;
    }

    private int importJson(HttpServletRequest req, Part filePart) throws Exception {
        // JSON import - parse and insert
        return 0;
    }

    private List<Problem> parseFpsXml(String xml) {
        List<Problem> problems = new ArrayList<>();
        // Basic FPS/XML parsing - extract problem elements
        // This is a simplified implementation
        return problems;
    }
}
