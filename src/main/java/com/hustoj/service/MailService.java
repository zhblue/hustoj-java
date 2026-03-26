package com.hustoj.service;

import com.hustoj.db.DB;
import com.hustoj.entity.Mail;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MailService {

    private final JdbcTemplate jdbc;

    public MailService() {
        this.jdbc = DB.getJdbcTemplate();
    }

    public MailService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Mail findById(int mailId) {
        String sql = "SELECT * FROM mail WHERE mail_id=?";
        List<Mail> list = jdbc.query(sql, new MailRowMapper(), mailId);
        return list.isEmpty() ? null : list.get(0);
    }

    public List<Mail> findByToUser(String toUser, int offset, int limit) {
        String sql = "SELECT * FROM mail WHERE to_user=? AND defunct='N' ORDER BY mail_id DESC LIMIT ? OFFSET ?";
        return jdbc.query(sql, new MailRowMapper(), toUser, limit, offset);
    }

    public List<Mail> findByFromUser(String fromUser, int offset, int limit) {
        String sql = "SELECT * FROM mail WHERE from_user=? AND defunct='N' ORDER BY mail_id DESC LIMIT ? OFFSET ?";
        return jdbc.query(sql, new MailRowMapper(), fromUser, limit, offset);
    }

    public int countNewMail(String userId) {
        String sql = "SELECT COUNT(*) FROM mail WHERE to_user=? AND new_mail=1 AND defunct='N'";
        return jdbc.queryForObject(sql, Integer.class, userId);
    }

    public int countByToUser(String userId) {
        String sql = "SELECT COUNT(*) FROM mail WHERE to_user=? AND defunct='N'";
        return jdbc.queryForObject(sql, Integer.class, userId);
    }

    public int countByFromUser(String userId) {
        String sql = "SELECT COUNT(*) FROM mail WHERE from_user=? AND defunct='N'";
        return jdbc.queryForObject(sql, Integer.class, userId);
    }

    public int sendMail(String fromUser, String toUser, String title, String content) {
        String sql = "INSERT INTO mail (to_user, from_user, title, content, new_mail, in_date, defunct) VALUES (?, ?, ?, ?, 1, NOW(), 'N')";
        return jdbc.update(sql, toUser, fromUser, title, content);
    }

    public int markRead(int mailId) {
        String sql = "UPDATE mail SET new_mail=0 WHERE mail_id=?";
        return jdbc.update(sql, mailId);
    }

    public int markAllRead(String userId) {
        String sql = "UPDATE mail SET new_mail=0 WHERE to_user=? AND new_mail=1";
        return jdbc.update(sql, userId);
    }

    public int setDefunct(int mailId, String defunct) {
        String sql = "UPDATE mail SET defunct=? WHERE mail_id=?";
        return jdbc.update(sql, defunct, mailId);
    }

    public int delete(int mailId) {
        String sql = "DELETE FROM mail WHERE mail_id=?";
        return jdbc.update(sql, mailId);
    }

    private static class MailRowMapper implements RowMapper<Mail> {
        @Override
        public Mail mapRow(ResultSet rs, int rowNum) throws SQLException {
            Mail m = new Mail();
            m.setMailId(rs.getInt("mail_id"));
            m.setToUser(rs.getString("to_user"));
            m.setFromUser(rs.getString("from_user"));
            m.setTitle(rs.getString("title"));
            m.setContent(rs.getString("content"));
            m.setNewMail(rs.getBoolean("new_mail"));
            m.setReply(rs.getInt("reply"));
            m.setInDate(rs.getTimestamp("in_date"));
            m.setDefunct(rs.getString("defunct"));
            return m;
        }
    }
}
