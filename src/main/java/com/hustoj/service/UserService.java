package com.hustoj.service;

import com.hustoj.db.DB;
import com.hustoj.entity.LoginLog;
import com.hustoj.entity.Privilege;
import com.hustoj.entity.User;
import com.hustoj.util.SecurityUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public class UserService {

    private final JdbcTemplate jdbc;

    public UserService() {
        this.jdbc = DB.getJdbcTemplate();
    }

    public UserService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public User findById(String userId) {
        String sql = "SELECT * FROM users WHERE user_id=?";
        List<User> list = jdbc.query(sql, BeanPropertyRowMapper.newInstance(User.class), userId);
        return list.isEmpty() ? null : list.get(0);
    }

    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email=?";
        List<User> list = jdbc.query(sql, BeanPropertyRowMapper.newInstance(User.class), email);
        return list.isEmpty() ? null : list.get(0);
    }

    public List<User> findAll(int offset, int limit, String defunct) {
        String sql = defunct == null
            ? "SELECT * FROM users ORDER BY reg_time DESC LIMIT ? OFFSET ?"
            : "SELECT * FROM users WHERE defunct=? ORDER BY reg_time DESC LIMIT ? OFFSET ?";
        RowMapper<User> mapper = BeanPropertyRowMapper.newInstance(User.class);
        if (defunct == null) {
            return jdbc.query(sql, mapper, limit, offset);
        }
        return jdbc.query(sql, mapper, defunct, limit, offset);
    }

    public List<User> findAll() {
        return findAll(0, Integer.MAX_VALUE, null);
    }

    public int count(String defunct) {
        String sql = defunct == null
            ? "SELECT COUNT(*) FROM users"
            : "SELECT COUNT(*) FROM users WHERE defunct=?";
        if (defunct == null) {
            return jdbc.queryForObject(sql, Integer.class);
        }
        return jdbc.queryForObject(sql, Integer.class, defunct);
    }

    public int countAcUser() {
        String sql = "SELECT COUNT(DISTINCT user_id) FROM solution WHERE result=4";
        return jdbc.queryForObject(sql, Integer.class);
    }

    public boolean validateLogin(String userId, String password) {
        User user = findById(userId);
        if (user == null) return false;
        if (!"N".equals(user.getDefunct())) return false;
        return SecurityUtil.pwCheck(password, user.getPassword());
    }

    public boolean exists(String userId) {
        String sql = "SELECT COUNT(*) FROM users WHERE user_id=?";
        return jdbc.queryForObject(sql, Integer.class, userId) > 0;
    }

    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email=?";
        return jdbc.queryForObject(sql, Integer.class, email) > 0;
    }

    public int register(String userId, String password, String email, String nick, String school, String ip) {
        if (exists(userId)) return -1;
        if (emailExists(email)) return -2;
        if (!SecurityUtil.isValidUserName(userId)) return -3;

        String hashedPassword = SecurityUtil.pwGen(password, false);
        String sql = "INSERT INTO users (user_id, password, email, nick, school, ip, reg_time, expiry_date, defunct, submit, solved, volume, language, group_name, activecode, starred) " +
                     "VALUES (?, ?, ?, ?, ?, ?, NOW(), ?, 'N', 0, 0, 1, 1, '', '', 0)";
        LocalDate expiryDate = LocalDate.now().plusDays(DB.getExpiryDays());
        return jdbc.update(sql, userId, hashedPassword, email, nick, school, ip, expiryDate);
    }

    public int updatePassword(String userId, String newPassword) {
        String hashed = SecurityUtil.pwGen(newPassword, false);
        String sql = "UPDATE users SET password=? WHERE user_id=?";
        return jdbc.update(sql, hashed, userId);
    }

    public int updateNick(String userId, String nick) {
        String sql = "UPDATE users SET nick=? WHERE user_id=?";
        return jdbc.update(sql, nick, userId);
    }

    public int updateSchool(String userId, String school) {
        String sql = "UPDATE users SET school=? WHERE user_id=?";
        return jdbc.update(sql, school, userId);
    }

    public int updateLanguage(String userId, int language) {
        String sql = "UPDATE users SET language=? WHERE user_id=?";
        return jdbc.update(sql, language, userId);
    }

    public int updateVolume(String userId, int volume) {
        String sql = "UPDATE users SET volume=? WHERE user_id=?";
        return jdbc.update(sql, volume, userId);
    }

    public int setDefunct(String userId, String defunct) {
        String sql = "UPDATE users SET defunct=? WHERE user_id=?";
        return jdbc.update(sql, defunct, userId);
    }

    public int updateAccessTime(String userId, String ip) {
        String sql = "UPDATE users SET accessTime=NOW(), ip=? WHERE user_id=?";
        return jdbc.update(sql, ip, userId);
    }

    public int incrementSubmit(String userId) {
        String sql = "UPDATE users SET submit=submit+1 WHERE user_id=?";
        return jdbc.update(sql, userId);
    }

    public int incrementSolved(String userId) {
        String sql = "UPDATE users SET solved=solved+1 WHERE user_id=?";
        return jdbc.update(sql, userId);
    }

    public int delete(String userId) {
        String sql = "DELETE FROM users WHERE user_id=?";
        return jdbc.update(sql, userId);
    }

    public int insertLoginLog(String userId, String password, String ip) {
        String sql = "INSERT INTO loginlog (user_id, password, ip, time) VALUES (?, ?, ?, NOW())";
        return jdbc.update(sql, userId, password, ip);
    }

    public List<LoginLog> getLoginLogs(String userId, int limit) {
        String sql = "SELECT * FROM loginlog WHERE user_id=? ORDER BY time DESC LIMIT ?";
        return jdbc.query(sql, BeanPropertyRowMapper.newInstance(LoginLog.class), userId, limit);
    }

    public String getLastLoginIp(String userId) {
        String sql = "SELECT ip FROM loginlog WHERE user_id=? ORDER BY time DESC LIMIT 1";
        List<String> list = jdbc.queryForList(sql, String.class, userId);
        return list.isEmpty() ? null : list.get(0);
    }

    public int countLoginFail(String userId, int minutes) {
        String sql = "SELECT COUNT(*) FROM loginlog WHERE user_id=? AND time > DATE_SUB(NOW(), INTERVAL ? MINUTE) AND password IS NOT NULL";
        return jdbc.queryForObject(sql, Integer.class, userId, minutes);
    }

    public int addPrivilege(String userId, String rightStr, String valueStr) {
        String sql = "INSERT INTO privilege (user_id, rightstr, valuestr, defunct) VALUES (?, ?, ?, 'N')";
        return jdbc.update(sql, userId, rightStr, valueStr);
    }

    public int deletePrivilege(String userId, String rightStr) {
        String sql = "DELETE FROM privilege WHERE user_id=? AND rightstr=?";
        return jdbc.update(sql, userId, rightStr);
    }

    public List<Privilege> getPrivileges(String userId) {
        String sql = "SELECT * FROM privilege WHERE user_id=? AND defunct='N'";
        return jdbc.query(sql, BeanPropertyRowMapper.newInstance(Privilege.class), userId);
    }

    public boolean hasPrivilege(String userId, String rightStr) {
        String sql = "SELECT COUNT(*) FROM privilege WHERE user_id=? AND rightstr=? AND defunct='N'";
        return jdbc.queryForObject(sql, Integer.class, userId, rightStr) > 0;
    }

    public int setPrivilegeDefunct(String userId, String rightStr, String defunct) {
        String sql = "UPDATE privilege SET defunct=? WHERE user_id=? AND rightstr=?";
        return jdbc.update(sql, defunct, userId, rightStr);
    }

    public List<User> search(String keyword, int limit) {
        String sql = "SELECT * FROM users WHERE user_id LIKE ? OR nick LIKE ? OR school LIKE ? LIMIT ?";
        String kw = "%" + keyword + "%";
        return jdbc.query(sql, BeanPropertyRowMapper.newInstance(User.class), kw, kw, kw, limit);
    }

    public List<User> findByGroup(String groupName, int offset, int limit) {
        String sql = "SELECT * FROM users WHERE group_name=? ORDER BY solved DESC, submit ASC LIMIT ? OFFSET ?";
        return jdbc.query(sql, BeanPropertyRowMapper.newInstance(User.class), groupName, limit, offset);
    }

    public int countByGroup(String groupName) {
        String sql = "SELECT COUNT(*) FROM users WHERE group_name=?";
        return jdbc.queryForObject(sql, Integer.class, groupName);
    }

    public List<String> getAllGroups() {
        String sql = "SELECT DISTINCT group_name FROM users WHERE group_name != '' ORDER BY group_name";
        return jdbc.queryForList(sql, String.class);
    }

    public List<User> getTopSolved(int limit) {
        String sql = "SELECT * FROM users WHERE defunct='N' ORDER BY solved DESC, submit ASC LIMIT ?";
        return jdbc.query(sql, BeanPropertyRowMapper.newInstance(User.class), limit);
    }

    public int batchImport(List<User> users) {
        String sql = "INSERT INTO users (user_id, password, email, nick, school, ip, reg_time, expiry_date, defunct, submit, solved, volume, language, group_name) " +
                     "VALUES (?, ?, ?, ?, ?, ?, NOW(), '2099-01-01', 'N', 0, 0, 1, 1, ?)";
        int count = 0;
        for (User u : users) {
            try {
                if (!exists(u.getUserId())) {
                    String hashed = u.getPassword() != null ? u.getPassword() : SecurityUtil.pwGen("123456", false);
                    jdbc.update(sql, u.getUserId(), hashed, u.getEmail(), u.getNick(), u.getSchool(), u.getIp(), u.getGroupName());
                    count++;
                }
            } catch (Exception e) {
                // skip duplicates
            }
        }
        return count;
    }

    public boolean isIpLimitToOneIp(String userId, String currentIp) {
        String lastIp = getLastLoginIp(userId);
        if (lastIp == null) return true;
        return lastIp.equals(currentIp);
    }
}
