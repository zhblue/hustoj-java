package com.hustoj.service;

import com.hustoj.db.DB;
import com.hustoj.entity.Solution;
import com.hustoj.entity.SourceCode;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class SolutionService {

    private final JdbcTemplate jdbc;

    public SolutionService() {
        this.jdbc = DB.getJdbcTemplate();
    }

    public SolutionService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Solution findById(int solutionId) {
        String sql = "SELECT * FROM solution WHERE solution_id=?";
        List<Solution> list = jdbc.query(sql, new SolutionRowMapper(), solutionId);
        return list.isEmpty() ? null : list.get(0);
    }

    public List<Solution> findByUserId(String userId, int offset, int limit) {
        String sql = "SELECT * FROM solution WHERE user_id=? ORDER BY solution_id DESC LIMIT ? OFFSET ?";
        return jdbc.query(sql, new SolutionRowMapper(), userId, limit, offset);
    }

    public List<Solution> findByProblemId(int problemId, int offset, int limit) {
        String sql = "SELECT * FROM solution WHERE problem_id=? ORDER BY solution_id DESC LIMIT ? OFFSET ?";
        return jdbc.query(sql, new SolutionRowMapper(), problemId, limit, offset);
    }

    public List<Solution> findByContestId(int contestId, int offset, int limit) {
        String sql = "SELECT * FROM solution WHERE contest_id=? ORDER BY solution_id DESC LIMIT ? OFFSET ?";
        return jdbc.query(sql, new SolutionRowMapper(), contestId, limit, offset);
    }

    public List<Solution> findAll(int offset, int limit) {
        String sql = "SELECT * FROM solution ORDER BY solution_id DESC LIMIT ? OFFSET ?";
        return jdbc.query(sql, new SolutionRowMapper(), limit, offset);
    }

    public List<Solution> findByUserIdAndProblemId(String userId, int problemId, int contestId, int offset, int limit) {
        String sql = "SELECT * FROM solution WHERE user_id=? AND problem_id=? AND contest_id=? ORDER BY solution_id DESC LIMIT ? OFFSET ?";
        return jdbc.query(sql, new SolutionRowMapper(), userId, problemId, contestId, limit, offset);
    }

    public int count() {
        return jdbc.queryForObject("SELECT COUNT(*) FROM solution", Integer.class);
    }

    public int countByUserId(String userId) {
        return jdbc.queryForObject("SELECT COUNT(*) FROM solution WHERE user_id=?", Integer.class, userId);
    }

    public int countByProblemId(int problemId) {
        return jdbc.queryForObject("SELECT COUNT(*) FROM solution WHERE problem_id=?", Integer.class, problemId);
    }

    public int countByResult(int result) {
        return jdbc.queryForObject("SELECT COUNT(*) FROM solution WHERE result=?", Integer.class, result);
    }

    public int countPending() {
        return jdbc.queryForObject("SELECT COUNT(*) FROM solution WHERE result<4", Integer.class);
    }

    public int insert(Solution s) {
        String sql = "INSERT INTO solution (problem_id, user_id, nick, time, memory, in_date, result, language, ip, contest_id, valid, num, code_length, judgetime, pass_rate, first_time, lint_error, judger, remote_oj, remote_id) VALUES (?, ?, ?, ?, ?, NOW(), ?, ?, ?, ?, ?, ?, ?, NOW(), ?, ?, ?, ?, ?, ?)";
        return jdbc.update(sql, s.getProblemId(), s.getUserId(), s.getNick(), s.getTime(), s.getMemory(), s.getResult(), s.getLanguage(), s.getIp(), s.getContestId(), s.getValid(), s.getNum(), s.getCodeLength(), BigDecimal.valueOf(s.getPassRate()), s.isFirstTime() ? 1 : 0, s.getLintError(), s.getJudge(), s.getRemoteOj(), s.getRemoteId());
    }

    public int updateResult(int solutionId, int result) {
        String sql = "UPDATE solution SET result=?, judgetime=NOW() WHERE solution_id=?";
        return jdbc.update(sql, result, solutionId);
    }

    public int updateResult(int solutionId, int result, int time, int memory, double passRate) {
        String sql = "UPDATE solution SET result=?, time=?, memory=?, pass_rate=?, judgetime=NOW() WHERE solution_id=?";
        return jdbc.update(sql, result, time, memory, BigDecimal.valueOf(passRate), solutionId);
    }

    public int updateFirstAC(int solutionId) {
        String sql = "UPDATE solution SET first_time=1 WHERE solution_id=?";
        return jdbc.update(sql, solutionId);
    }

    public int rejudge(int solutionId) {
        String sql = "UPDATE solution SET result=1, judgetime=NOW() WHERE solution_id=?"; // 1=PR pending rejudge
        return jdbc.update(sql, solutionId);
    }

    public int rejudgeByProblemId(int problemId) {
        String sql = "UPDATE solution SET result=1 WHERE problem_id=?";
        return jdbc.update(sql, problemId);
    }

    public int rejudgeByContestId(int contestId) {
        String sql = "UPDATE solution SET result=1 WHERE contest_id=?";
        return jdbc.update(sql, contestId);
    }

    public int delete(int solutionId) {
        String sql = "DELETE FROM solution WHERE solution_id=?";
        return jdbc.update(sql, solutionId);
    }

    public int deleteByProblemId(int problemId) {
        String sql = "DELETE FROM solution WHERE problem_id=?";
        return jdbc.update(sql, problemId);
    }

    public boolean isFirstAC(String userId, int problemId) {
        String sql = "SELECT COUNT(*) FROM solution WHERE user_id=? AND problem_id=? AND result=4 AND first_time=1";
        return jdbc.queryForObject(sql, Integer.class, userId, problemId) == 0;
    }

    public String getSourceCode(int solutionId) {
        String sql = "SELECT source FROM source_code WHERE solution_id=?";
        List<String> list = jdbc.queryForList(sql, String.class, solutionId);
        return list.isEmpty() ? null : list.get(0);
    }

    public int saveSourceCode(int solutionId, String source) {
        String sql = "INSERT INTO source_code (solution_id, source) VALUES (?, ?)";
        return jdbc.update(sql, solutionId, source);
    }

    public int updateSourceCode(int solutionId, String source) {
        String sql = "UPDATE source_code SET source=? WHERE solution_id=?";
        return jdbc.update(sql, source, solutionId);
    }

    public String getCompileInfo(int solutionId) {
        String sql = "SELECT error FROM compileinfo WHERE solution_id=?";
        List<String> list = jdbc.queryForList(sql, String.class, solutionId);
        return list.isEmpty() ? null : list.get(0);
    }

    public int saveCompileInfo(int solutionId, String error) {
        String sql = "INSERT INTO compileinfo (solution_id, error) VALUES (?, ?)";
        return jdbc.update(sql, solutionId, error);
    }

    public int updateCompileInfo(int solutionId, String error) {
        String sql = "UPDATE compileinfo SET error=? WHERE solution_id=?";
        return jdbc.update(sql, error, solutionId);
    }

    public String getRuntimeInfo(int solutionId) {
        String sql = "SELECT error FROM runtimeinfo WHERE solution_id=?";
        List<String> list = jdbc.queryForList(sql, String.class, solutionId);
        return list.isEmpty() ? null : list.get(0);
    }

    public int saveRuntimeInfo(int solutionId, String error) {
        String sql = "INSERT INTO runtimeinfo (solution_id, error) VALUES (?, ?)";
        return jdbc.update(sql, solutionId, error);
    }

    public int updateRuntimeInfo(int solutionId, String error) {
        String sql = "UPDATE runtimeinfo SET error=? WHERE solution_id=?";
        return jdbc.update(sql, error, solutionId);
    }

    public List<Solution> search(String userId, Integer problemId, Integer result, Integer language, String ip, int offset, int limit) {
        StringBuilder sql = new StringBuilder("SELECT * FROM solution WHERE 1=1");
        if (userId != null && !userId.isEmpty()) sql.append(" AND user_id='").append(userId).append("'");
        if (problemId != null) sql.append(" AND problem_id=").append(problemId);
        if (result != null) sql.append(" AND result=").append(result);
        if (language != null) sql.append(" AND language=").append(language);
        if (ip != null && !ip.isEmpty()) sql.append(" AND ip='").append(ip).append("'");
        sql.append(" ORDER BY solution_id DESC LIMIT ").append(limit).append(" OFFSET ").append(offset);
        return jdbc.query(sql.toString(), new SolutionRowMapper());
    }

    public int countSim(int solutionId) {
        String sql = "SELECT COUNT(*) FROM sim WHERE s_id=?";
        return jdbc.queryForObject(sql, Integer.class, solutionId);
    }

    public int insertSim(int sId, int simSId, int sim) {
        String sql = "INSERT INTO sim (s_id, sim_s_id, sim) VALUES (?, ?, ?)";
        return jdbc.update(sql, sId, simSId, sim);
    }

    public int deleteSim(int solutionId) {
        String sql = "DELETE FROM sim WHERE s_id=?";
        return jdbc.update(sql, solutionId);
    }

    public int getCustomInput(int solutionId) {
        String sql = "SELECT COUNT(*) FROM custominput WHERE solution_id=?";
        return jdbc.queryForObject(sql, Integer.class, solutionId);
    }

    public String getCustomInputText(int solutionId) {
        String sql = "SELECT input_text FROM custominput WHERE solution_id=?";
        List<String> list = jdbc.queryForList(sql, String.class, solutionId);
        return list.isEmpty() ? null : list.get(0);
    }

    public int saveCustomInput(int solutionId, String inputText) {
        String sql = "INSERT INTO custominput (solution_id, input_text) VALUES (?, ?)";
        return jdbc.update(sql, solutionId, inputText);
    }

    public int deleteCustomInput(int solutionId) {
        String sql = "DELETE FROM custominput WHERE solution_id=?";
        return jdbc.update(sql, solutionId);
    }

    private static class SolutionRowMapper implements RowMapper<Solution> {
        @Override
        public Solution mapRow(ResultSet rs, int rowNum) throws SQLException {
            Solution s = new Solution();
            s.setSolutionId(rs.getInt("solution_id"));
            s.setProblemId(rs.getInt("problem_id"));
            s.setUserId(rs.getString("user_id"));
            s.setNick(rs.getString("nick"));
            s.setTime(rs.getInt("time"));
            s.setMemory(rs.getInt("memory"));
            s.setInDate(rs.getTimestamp("in_date"));
            s.setResult(rs.getInt("result"));
            s.setLanguage(rs.getInt("language"));
            s.setIp(rs.getString("ip"));
            s.setContestId(rs.getInt("contest_id"));
            s.setValid(rs.getInt("valid"));
            s.setNum(rs.getInt("num"));
            s.setCodeLength(rs.getInt("code_length"));
            s.setJudgeTime(rs.getTimestamp("judgetime"));
            s.setPassRate(rs.getBigDecimal("pass_rate") != null ? rs.getBigDecimal("pass_rate").doubleValue() : 0);
            s.setFirstTime(rs.getBoolean("first_time"));
            s.setLintError(rs.getInt("lint_error"));
            s.setJudge(rs.getString("judger"));
            s.setRemoteOj(rs.getString("remote_oj"));
            s.setRemoteId(rs.getString("remote_id"));
            return s;
        }
    }
}
