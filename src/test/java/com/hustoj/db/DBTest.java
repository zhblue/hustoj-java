package com.hustoj.db;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DBTest {

    @Test
    void testGetResultName() {
        // DB stores names with underscores: "Pending_Rejudging", "Wrong_Answer", "Time_Limit_Exceed"
        assertEquals("Pending", DB.getResultName(0));
        assertEquals("Accepted", DB.getResultName(4));
        assertEquals("Wrong_Answer", DB.getResultName(6));
        assertEquals("Time_Limit_Exceed", DB.getResultName(7));
        assertEquals("Compile_Error", DB.getResultName(11));
        assertEquals("Submitting", DB.getResultName(15));
        assertEquals("Remote_Pending", DB.getResultName(16));
        assertEquals("Remote_Judging", DB.getResultName(17));
    }

    @Test
    void testGetResultNameUnknown() {
        assertEquals("Unknown", DB.getResultName(999));
        assertEquals("Unknown", DB.getResultName(-1));
    }

    @Test
    void testGetResultColor() {
        assertNotNull(DB.getResultColor(0));
        assertNotNull(DB.getResultColor(4));
        assertNotNull(DB.getResultColor(11));
        // Accepted is green
        assertEquals("label label-success", DB.getResultColor(4));
    }

    @Test
    void testGetLanguageName() {
        assertEquals("C", DB.getLanguageName(0));
        assertEquals("C++", DB.getLanguageName(1));
        assertEquals("Java", DB.getLanguageName(3));
        assertEquals("Python", DB.getLanguageName(6));
        assertEquals("Go", DB.getLanguageName(17));
    }

    @Test
    void testGetLanguageExt() {
        assertEquals("c", DB.getLanguageExt(0));
        assertEquals("cc", DB.getLanguageExt(1));  // C++ uses .cc extension
        assertEquals("java", DB.getLanguageExt(3));
        assertEquals("rb", DB.getLanguageExt(4));   // Ruby uses .rb
        assertEquals("py", DB.getLanguageExt(6));   // Python uses .py
        assertEquals("js", DB.getLanguageExt(16));
        assertEquals("go", DB.getLanguageExt(17));
    }

    @Test
    void testGetLanguageExtUnknown() {
        assertEquals("", DB.getLanguageExt(-1));
        assertEquals("", DB.getLanguageExt(999));
    }

    @Test
    void testGetLanguageNameUnknown() {
        assertEquals("Unknown", DB.getLanguageName(-1));
        assertEquals("Unknown", DB.getLanguageName(999));
    }

    @Test
    void testSessionConstants() {
        assertEquals("HUSTOJ_user_id", DB.SESSION_USER_ID);
        assertEquals("HUSTOJ_nick", DB.SESSION_NICK);
        assertEquals("HUSTOJ_administrator", DB.SESSION_ADministrator);
        assertEquals("HUSTOJ_csrf_token", DB.SESSION_CSRF_TOKEN);
    }

    @Test
    void testResultNameCount() {
        assertEquals(18, DB.JUDGE_RESULT_NAMES.length, "Should have 18 result names (0-17)");
        assertEquals(18, DB.JUDGE_RESULT_COLORS.length, "Should have 18 result colors (0-17)");
    }

    @Test
    void testLanguageArrayLength() {
        assertEquals(DB.LANGUAGE_NAMES.length, DB.LANGUAGE_EXT.length);
    }

    @Test
    void testBallColors() {
        assertNotNull(DB.BALL_COLORS);
        assertTrue(DB.BALL_COLORS.length > 0);
        assertEquals("#66cccc", DB.BALL_COLORS[0]);
        assertEquals("red", DB.BALL_COLORS[1]);
    }
}
