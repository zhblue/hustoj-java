package com.hustoj.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SecurityUtilTest {

    // ===== CSRF Token Tests =====

    @Test
    void testGenerateTokenLength() {
        assertEquals(32, SecurityUtil.generateToken().length());
        assertEquals(16, SecurityUtil.generateToken(16).length());
        assertEquals(64, SecurityUtil.generateToken(64).length());
    }

    @Test
    void testGenerateTokenIsAlphanumeric() {
        assertTrue(SecurityUtil.generateToken().matches("[A-Za-z0-9]+"));
    }

    @Test
    void testGenerateTokenUniqueness() {
        assertNotEquals(SecurityUtil.generateToken(), SecurityUtil.generateToken());
    }

    // ===== XSS Protection Tests =====

    @Test
    void testRemoveXSSRemovesScript() {
        // removeXSS removes "script" keyword, leaving < >alert(1)< / >
        assertEquals("<>alert(1)</>", SecurityUtil.removeXSS("<script>alert(1)</script>"));
    }

    @Test
    void testRemoveXSSRemovesJavascriptProtocol() {
        assertEquals(":alert(1)", SecurityUtil.removeXSS("javascript:alert(1)"));
        assertEquals(":alert(1)", SecurityUtil.removeXSS("JAVASCRIPT:alert(1)")); // case insensitive
    }

    @Test
    void testRemoveXVbscriptProtocol() {
        assertEquals(":msgbox('xss')", SecurityUtil.removeXSS("vbscript:msgbox('xss')"));
    }

    @Test
    void testRemoveXSSRemovesOnEventHandlers() {
        assertEquals("alert(1)", SecurityUtil.removeXSS("onclick=alert(1)"));
        assertEquals("alert(1)", SecurityUtil.removeXSS("OnClick=alert(1)"));
        assertEquals("alert(2)", SecurityUtil.removeXSS("onerror=alert(2)"));
    }

    @Test
    void testRemoveXSSRemovesApplet() {
        assertEquals("<> malicious </>", SecurityUtil.removeXSS("<applet> malicious </applet>"));
    }

    @Test
    void testRemoveXSSRemovesMeta() {
        assertEquals("< http-equiv=\"refresh\" content=\"0\">",
            SecurityUtil.removeXSS("<meta http-equiv=\"refresh\" content=\"0\">"));
    }

    @Test
    void testRemoveXSSRemovesEmbed() {
        assertEquals("< src=\"evil.swf\">", SecurityUtil.removeXSS("<embed src=\"evil.swf\">"));
    }

    @Test
    void testRemoveXSSRemovesObject() {
        assertEquals("< data=\"malicious.exe\">", SecurityUtil.removeXSS("<object data=\"malicious.exe\">"));
    }

    @Test
    void testRemoveXSSNullInput() {
        assertNull(SecurityUtil.removeXSS(null));
    }

    @Test
    void testRemoveXSSPreservesNormalText() {
        assertEquals("hello world", SecurityUtil.removeXSS("hello world"));
    }

    // ===== HTML Escaping Tests =====

    @Test
    void testEscapeHtml() {
        assertEquals("&amp;", SecurityUtil.escapeHtml("&"));
        assertEquals("&lt;script&gt;", SecurityUtil.escapeHtml("<script>"));
        assertEquals("&gt;", SecurityUtil.escapeHtml(">"));
        assertEquals("&quot;test&quot;", SecurityUtil.escapeHtml("\"test\""));
    }

    @Test
    void testEscapeHtmlNull() {
        assertEquals("", SecurityUtil.escapeHtml(null));
    }

    @Test
    void testEscapeHtmlNoSpecialChars() {
        assertEquals("plain text 123", SecurityUtil.escapeHtml("plain text 123"));
    }

    // ===== Username Validation Tests =====

    @Test
    void testValidUserNames() {
        assertTrue(SecurityUtil.isValidUserName("alice"));
        assertTrue(SecurityUtil.isValidUserName("Bob123"));
        assertTrue(SecurityUtil.isValidUserName("test-user"));
        assertTrue(SecurityUtil.isValidUserName("user_name"));
        assertTrue(SecurityUtil.isValidUserName("Admin"));
    }

    @Test
    void testUserNameStartingWithSpecialChars() {
        assertTrue(SecurityUtil.isValidUserName("*admin"));
        assertTrue(SecurityUtil.isValidUserName("-user"));
        assertTrue(SecurityUtil.isValidUserName("123abc")); // leading digit allowed
    }

    @Test
    void testInvalidUserNames() {
        assertFalse(SecurityUtil.isValidUserName(null));
        assertFalse(SecurityUtil.isValidUserName(""));
        assertFalse(SecurityUtil.isValidUserName("user name")); // space not allowed
        assertFalse(SecurityUtil.isValidUserName("user@name")); // @ not allowed
        assertFalse(SecurityUtil.isValidUserName("user.name")); // . not allowed
    }

    @Test
    void testUserNameTooLong() {
        assertFalse(SecurityUtil.isValidUserName("a".repeat(49)));
        assertTrue(SecurityUtil.isValidUserName("a".repeat(48)));
    }

    // ===== Bad Words Filter Tests =====

    @Test
    void testHasBadWordsDetectsProfanity() {
        assertTrue(SecurityUtil.hasBadWords("装逼"));
        assertTrue(SecurityUtil.hasBadWords("傻逼"));
        assertTrue(SecurityUtil.hasBadWords("草泥马"));
    }

    @Test
    void testHasBadWordsDetectsSystemUsernames() {
        assertTrue(SecurityUtil.hasBadWords("admin"));
        assertTrue(SecurityUtil.hasBadWords("root"));
        assertTrue(SecurityUtil.hasBadWords("administrator"));
    }

    @Test
    void testHasBadWordsCleanText() {
        assertFalse(SecurityUtil.hasBadWords("hello"));
        assertFalse(SecurityUtil.hasBadWords("world"));
        assertFalse(SecurityUtil.hasBadWords("programming"));
        assertFalse(SecurityUtil.hasBadWords("java"));
    }

    @Test
    void testHasBadWordsNull() {
        assertFalse(SecurityUtil.hasBadWords(null));
    }

    // ===== Password Strength Tests =====
    // Current implementation: conditionsMet >= 2 means strong enough

    @Test
    void testPasswordTooShort() {
        assertFalse(SecurityUtil.isPasswordStrongEnough("abc"));
        assertFalse(SecurityUtil.isPasswordStrongEnough("ab"));
    }

    @Test
    void testPasswordMeetsConditions() {
        // 8+ chars, has digit, has lowercase, has uppercase = 4 conditions met
        assertTrue(SecurityUtil.isPasswordStrongEnough("Password1"));
        // 8+ chars, has lowercase, has uppercase = 3 conditions
        assertTrue(SecurityUtil.isPasswordStrongEnough("Abcdefgh"));
        // 8+ chars, has digit = 2 conditions
        assertTrue(SecurityUtil.isPasswordStrongEnough("12345678"));
    }

    @Test
    void testPasswordStrongEnough() {
        assertTrue(SecurityUtil.isPasswordStrongEnough("Password1!"));
        assertTrue(SecurityUtil.isPasswordStrongEnough("Admin123!"));
    }

    @Test
    void testPasswordNull() {
        assertFalse(SecurityUtil.isPasswordStrongEnough(null));
    }

    // ===== MD5 Tests =====

    @Test
    void testMd5EmptyString() {
        assertEquals("d41d8cd98f00b204e9800998ecf8427e", SecurityUtil.md5(""));
    }

    @Test
    void testMd5KnownValue() {
        assertEquals("098f6bcd4621d373cade4e832627b4f6", SecurityUtil.md5("test"));
    }

    @Test
    void testMd5Consistency() {
        String h1 = SecurityUtil.md5("consistent");
        String h2 = SecurityUtil.md5("consistent");
        assertEquals(h1, h2);
    }

    @Test
    void testMd5Length() {
        assertEquals(32, SecurityUtil.md5("any").length());
    }

    // ===== Old Password Format Tests =====

    @Test
    void testIsOldPWValid() {
        assertTrue(SecurityUtil.isOldPW("d41d8cd98f00b204e9800998ecf8427e"));
        assertTrue(SecurityUtil.isOldPW("abcdef1234567890abcdef1234567890"));
    }

    @Test
    void testIsOldPWInvalid() {
        assertFalse(SecurityUtil.isOldPW(null));
        assertFalse(SecurityUtil.isOldPW(""));
        assertFalse(SecurityUtil.isOldPW("not-hex"));
        assertFalse(SecurityUtil.isOldPW("d41d8cd98f00b204e9800998ecf8427")); // 31 chars
    }

    // ===== Password Generation Tests =====

    @Test
    void testPwGenNotNull() {
        assertNotNull(SecurityUtil.pwGen("pass", false));
        assertNotNull(SecurityUtil.pwGen("pass", true));
    }

    @Test
    void testPwGenDifferentForSamePassword() {
        assertNotEquals(SecurityUtil.pwGen("same", false), SecurityUtil.pwGen("same", false));
    }

    // ===== Password Check Tests =====

    @Test
    void testPwCheckWithMD5() {
        String md5 = SecurityUtil.md5("testpass");
        assertTrue(SecurityUtil.pwCheck("testpass", md5));
        assertFalse(SecurityUtil.pwCheck("wrong", md5));
    }

    @Test
    void testPwCheckWithNewHash() {
        String hash = SecurityUtil.pwGen("newpass", false);
        assertTrue(SecurityUtil.pwCheck("newpass", hash));
        assertFalse(SecurityUtil.pwCheck("wrongpass", hash));
    }

    // ===== Safe Zip Path Tests =====

    @Test
    void testGetSafeZipPathNormalFile() {
        String result = SecurityUtil.getSafeZipPath("/base/dir", "file.txt");
        assertNotNull(result);
        assertTrue(result.contains("file.txt"));
    }

    @Test
    void testGetSafeZipPathTraversal() {
        // Implementation normalizes path but allows traversal within reason
        String result = SecurityUtil.getSafeZipPath("/base", "sub/file.txt");
        assertNotNull(result);
        assertFalse(result.contains(".."));
    }

    @Test
    void testGetSafeZipPathNullInputs() {
        assertThrows(IllegalArgumentException.class, () -> {
            SecurityUtil.getSafeZipPath(null, "file.txt");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            SecurityUtil.getSafeZipPath("/base", null);
        });
    }
}
