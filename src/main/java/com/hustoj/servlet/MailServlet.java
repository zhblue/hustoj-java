package com.hustoj.servlet;

import com.hustoj.db.DB;
import com.hustoj.entity.Mail;
import com.hustoj.service.MailService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class MailServlet extends BaseServlet {

    private final MailService mailService = new MailService();

    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = getUserId(req);
        if (userId == null) {
            redirect(req, resp, "/loginpage");
            return;
        }

        int page = paramInt(req, "page", 1);
        int limit = 20;
        int offset = (page - 1) * limit;

        List<Mail> mails = mailService.findByToUser(userId, offset, limit);
        int total = mailService.countByToUser(userId);
        int newCount = mailService.countNewMail(userId);
        int pageCount = (total + limit - 1) / limit;

        setAttr(req, "mails", mails);
        setAttr(req, "page", page);
        setAttr(req, "pageCount", pageCount);
        setAttr(req, "newCount", newCount);
        setAttr(req, "OJ_NAME", DB.getOjName());
        forward(req, resp, "/WEB-INF/views/mail/mail.jsp");
    }

    public void view(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = getUserId(req);
        if (userId == null) {
            redirect(req, resp, "/loginpage");
            return;
        }

        int mailId = paramInt(req, "mid", 0);
        Mail mail = mailService.findById(mailId);
        if (mail == null) {
            setAttr(req, "error", "Mail not found");
            forward(req, resp, "/WEB-INF/views/error.jsp");
            return;
        }

        if (!mail.getToUser().equals(userId) && !mail.getFromUser().equals(userId)) {
            setAttr(req, "error", "Access denied");
            forward(req, resp, "/WEB-INF/views/error.jsp");
            return;
        }

        if (mail.isNewMail() && mail.getToUser().equals(userId)) {
            mailService.markRead(mailId);
        }

        setAttr(req, "mail", mail);
        forward(req, resp, "/WEB-INF/views/mail/viewmail.jsp");
    }

    public void send(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = getUserId(req);
        if (userId == null) {
            redirect(req, resp, "/loginpage");
            return;
        }

        String toUser = param(req, "to_user");
        String title = param(req, "title");
        String content = param(req, "content");

        if (toUser == null || title == null || content == null) {
            setAttr(req, "error", "All fields required");
            forward(req, resp, "/WEB-INF/views/mail/sendmail.jsp");
            return;
        }

        mailService.sendMail(userId, toUser, title, content);
        redirect(req, resp, "/mail");
    }
}
