package com.hustoj.service;

import com.hustoj.entity.Problem;
import com.hustoj.entity.Solution;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SolutionServiceTest {

    private SolutionService solutionService;
    private UserService userService;
    private ProblemService problemService;
    private JdbcTemplate jdbc;

    @BeforeEach
    void setUp() {
        jdbc = MySqlTestUtil.createJdbc();
        MySqlTestUtil.cleanTables(jdbc, "solution", "source_code", "problem", "users", "contest_problem", "contest");
        solutionService = new SolutionService(jdbc);
        userService = new UserService(jdbc);
        problemService = new ProblemService(jdbc);
    }

    @AfterEach
    void tearDown() {
        // Tables already cleaned before each test
    }

    private Problem makeProblem(String title) {
        Problem p = new Problem();
        p.setTitle(title);
        p.setDescription("Description");
        p.setInput("Input");
        p.setOutput("Output");
        p.setHint("Hint");
        p.setTimeLimit(new BigDecimal("1.000"));
        p.setMemoryLimit(256);
        p.setSpj("0");
        p.setSource("Test");
        return p;
    }

    @Test
    void testInsertAndFindById() {
        userService.register("testuser", "pass", "test@test.com", "Nick", "School", "127.0.0.1");
        Problem p = makeProblem("Test");
        int pid = problemService.insert(p);

        Solution s = new Solution();
        s.setProblemId(pid);
        s.setUserId("testuser");
        s.setLanguage(0);
        s.setIp("127.0.0.1");
        s.setContestId(0);
        s.setNum(-1);
        s.setCodeLength(100);
        s.setPassRate(0);

        int sid = solutionService.insert(s);
        assertTrue(sid > 0);

        Solution found = solutionService.findById(sid);
        assertNotNull(found);
        assertEquals(pid, found.getProblemId());
        assertEquals("testuser", found.getUserId());
        assertEquals(0, found.getLanguage());
    }

    @Test
    void testUpdateResult() {
        userService.register("testuser", "pass", "test@test.com", "Nick", "School", "127.0.0.1");
        Problem p = makeProblem("Test");
        int pid = problemService.insert(p);

        Solution s = new Solution();
        s.setProblemId(pid);
        s.setUserId("testuser");
        s.setLanguage(0);
        s.setIp("127.0.0.1");
        s.setContestId(0);
        s.setNum(-1);
        s.setCodeLength(100);
        int sid = solutionService.insert(s);

        solutionService.updateResult(sid, 4, 100, 256, 1.0);
        Solution updated = solutionService.findById(sid);
        assertEquals(4, updated.getResult());
        assertEquals(100, updated.getTime());
        assertEquals(256, updated.getMemory());
    }

    @Test
    void testRejudge() {
        userService.register("testuser", "pass", "test@test.com", "Nick", "School", "127.0.0.1");
        Problem p = makeProblem("Test");
        int pid = problemService.insert(p);

        Solution s = new Solution();
        s.setProblemId(pid);
        s.setUserId("testuser");
        s.setLanguage(0);
        s.setIp("127.0.0.1");
        s.setContestId(0);
        s.setNum(-1);
        s.setCodeLength(100);
        int sid = solutionService.insert(s);

        solutionService.rejudge(sid);
        assertEquals(1, solutionService.findById(sid).getResult());
    }

    @Test
    void testSaveAndGetSourceCode() {
        userService.register("testuser", "pass", "test@test.com", "Nick", "School", "127.0.0.1");
        Problem p = makeProblem("Test");
        int pid = problemService.insert(p);

        Solution s = new Solution();
        s.setProblemId(pid);
        s.setUserId("testuser");
        s.setLanguage(0);
        s.setIp("127.0.0.1");
        s.setContestId(0);
        s.setNum(-1);
        s.setCodeLength(100);
        int sid = solutionService.insert(s);

        solutionService.saveSourceCode(sid, "public class Main {}");
        assertEquals("public class Main {}", solutionService.getSourceCode(sid));
    }

    @Test
    void testSaveAndGetCompileInfo() {
        int sid = 1;
        solutionService.saveCompileInfo(sid, "Error: undefined reference");
        assertEquals("Error: undefined reference", solutionService.getCompileInfo(sid));
    }

    @Test
    void testSaveAndGetRuntimeInfo() {
        int sid = 1;
        solutionService.saveRuntimeInfo(sid, "Runtime Error: Segmentation Fault");
        assertEquals("Runtime Error: Segmentation Fault", solutionService.getRuntimeInfo(sid));
    }

    @Test
    void testCountPending() {
        assertEquals(0, solutionService.countPending());
    }

    @Test
    void testFindByUserId() {
        userService.register("testuser", "pass", "test@test.com", "Nick", "School", "127.0.0.1");
        Problem p = makeProblem("Test");
        int pid = problemService.insert(p);

        for (int i = 0; i < 3; i++) {
            Solution s = new Solution();
            s.setProblemId(pid);
            s.setUserId("testuser");
            s.setLanguage(0);
            s.setIp("127.0.0.1");
            s.setContestId(0);
            s.setNum(-1);
            s.setCodeLength(100);
            solutionService.insert(s);
        }

        List<Solution> sols = solutionService.findByUserId("testuser", 0, 10);
        assertEquals(3, sols.size());
    }

    @Test
    void testFirstAC() {
        userService.register("testuser", "pass", "test@test.com", "Nick", "School", "127.0.0.1");
        Problem p = makeProblem("Test");
        int pid = problemService.insert(p);

        assertTrue(solutionService.isFirstAC("testuser", pid));

        Solution s = new Solution();
        s.setProblemId(pid);
        s.setUserId("testuser");
        s.setLanguage(0);
        s.setIp("127.0.0.1");
        s.setContestId(0);
        s.setNum(-1);
        s.setCodeLength(100);
        int sid = solutionService.insert(s);

        solutionService.updateFirstAC(sid);
        assertFalse(solutionService.isFirstAC("testuser", pid));
    }
}
