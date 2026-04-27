package com.hustoj.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {

    @Test
    void testResultConstants() {
        assertEquals(0, Solution.RESULT_PD);
        assertEquals(1, Solution.RESULT_PR);
        assertEquals(2, Solution.RESULT_CI);
        assertEquals(3, Solution.RESULT_RJ);
        assertEquals(4, Solution.RESULT_AC);
        assertEquals(5, Solution.RESULT_PE);
        assertEquals(6, Solution.RESULT_WA);
        assertEquals(7, Solution.RESULT_TLE);
        assertEquals(8, Solution.RESULT_MLE);
        assertEquals(9, Solution.RESULT_OLE);
        assertEquals(10, Solution.RESULT_RE);
        assertEquals(11, Solution.RESULT_CE);
        assertEquals(12, Solution.RESULT_CO);
        assertEquals(13, Solution.RESULT_TR);
        assertEquals(14, Solution.RESULT_MC);
        assertEquals(15, Solution.RESULT_SUBMITTING);
        assertEquals(16, Solution.RESULT_REMOTE_PENDING);
        assertEquals(17, Solution.RESULT_REMOTE_JUDGING);
    }

    @Test
    void testSolutionBuilder() {
        Solution s = new Solution();
        s.setSolutionId(123);
        s.setProblemId(1001);
        s.setUserId("testuser");
        s.setResult(Solution.RESULT_AC);
        s.setTime(100);
        s.setMemory(256);
        s.setLanguage(0);
        s.setIp("127.0.0.1");
        s.setContestId(0);
        s.setNum(-1);
        s.setCodeLength(500);

        assertEquals(123, s.getSolutionId());
        assertEquals(1001, s.getProblemId());
        assertEquals("testuser", s.getUserId());
        assertEquals(Solution.RESULT_AC, s.getResult());
        assertEquals(100, s.getTime());
        assertEquals(256, s.getMemory());
        assertEquals(0, s.getLanguage());
        assertEquals("127.0.0.1", s.getIp());
        assertEquals(0, s.getContestId());
        assertEquals(-1, s.getNum());
        assertEquals(500, s.getCodeLength());
    }

    @Test
    void testSolutionDefaultValues() {
        Solution s = new Solution();
        assertEquals(0, s.getProblemId());
        assertNull(s.getUserId());
        assertEquals(0, s.getResult());
        assertEquals(0, s.getLanguage());
        assertEquals(0, s.getContestId());
        assertEquals(0, s.getNum());
        assertEquals(0, s.getValid()); // default valid is 0
    }

    @Test
    void testAc判定() {
        assertEquals(4, Solution.RESULT_AC);
        Solution s = new Solution();
        s.setResult(Solution.RESULT_AC);
        assertEquals(Solution.RESULT_AC, s.getResult());
    }

    @Test
    void testPending判定() {
        Solution s = new Solution();
        s.setResult(Solution.RESULT_PD);
        assertEquals(Solution.RESULT_PD, s.getResult());
        s.setResult(Solution.RESULT_PR);
        assertEquals(Solution.RESULT_PR, s.getResult());
        s.setResult(Solution.RESULT_CI);
        assertEquals(Solution.RESULT_CI, s.getResult());
        s.setResult(Solution.RESULT_RJ);
        assertEquals(Solution.RESULT_RJ, s.getResult());
    }

    @Test
    void testAllResultCodesAreUnique() {
        int[] codes = {
            Solution.RESULT_PD, Solution.RESULT_PR, Solution.RESULT_CI, Solution.RESULT_RJ,
            Solution.RESULT_AC, Solution.RESULT_PE, Solution.RESULT_WA, Solution.RESULT_TLE,
            Solution.RESULT_MLE, Solution.RESULT_OLE, Solution.RESULT_RE, Solution.RESULT_CE,
            Solution.RESULT_CO, Solution.RESULT_TR, Solution.RESULT_MC,
            Solution.RESULT_SUBMITTING, Solution.RESULT_REMOTE_PENDING, Solution.RESULT_REMOTE_JUDGING
        };
        for (int i = 0; i < codes.length; i++) {
            for (int j = i + 1; j < codes.length; j++) {
                assertNotEquals(codes[i], codes[j], "Duplicate result code at indices " + i + " and " + j);
            }
        }
    }

    @Test
    void testResultCodeMappings() {
        assertEquals(0, Solution.RESULT_PD);
        assertEquals(4, Solution.RESULT_AC);
        assertEquals(6, Solution.RESULT_WA);
        assertEquals(7, Solution.RESULT_TLE);
        assertEquals(8, Solution.RESULT_MLE);
        assertEquals(11, Solution.RESULT_CE);
    }
}
