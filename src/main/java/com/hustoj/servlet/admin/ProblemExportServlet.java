package com.hustoj.servlet.admin;
import com.hustoj.servlet.BaseServlet;

import com.hustoj.entity.Problem;
import com.hustoj.service.ProblemService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ProblemExportServlet extends BaseServlet {

    private final ProblemService problemService = new ProblemService();

    public void submit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) {
            redirect(req, resp, "/loginpage");
            return;
        }

        int problemId = paramInt(req, "problem_id", 0);
        String format = param(req, "format", "xml");

        Problem p = problemService.findById(problemId);
        if (p == null) {
            setAttr(req, "error", "Problem not found");
            forward(req, resp, "/WEB-INF/views/error.jsp");
            return;
        }

        resp.setContentType("application/xml");
        resp.setHeader("Content-Disposition", "attachment; filename=problem_" + problemId + ".xml");

        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xml.append("<fps version=\"1.0\">\n");
        xml.append("  <problem>\n");
        xml.append("    <title>").append(escape(p.getTitle())).append("</title>\n");
        xml.append("    <time_limit>").append(p.getTimeLimit()).append("</time_limit>\n");
        xml.append("    <memory_limit>").append(p.getMemoryLimit()).append("</memory_limit>\n");
        xml.append("    <description><![CDATA[").append(p.getDescription()).append("]]></description>\n");
        xml.append("    <input><![CDATA[").append(p.getInput()).append("]]></input>\n");
        xml.append("    <output><![CDATA[").append(p.getOutput()).append("]]></output>\n");
        xml.append("    <sample_input><![CDATA[").append(p.getSampleInput()).append("]]></sample_input>\n");
        xml.append("    <sample_output><![CDATA[").append(p.getSampleOutput()).append("]]></sample_output>\n");
        xml.append("    <hint><![CDATA[").append(p.getHint() != null ? p.getHint() : "").append("]]></hint>\n");
        xml.append("    <source>").append(escape(p.getSource())).append("</source>\n");
        xml.append("  </problem>\n");
        xml.append("</fps>");

        resp.getWriter().print(xml.toString());
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }
}
