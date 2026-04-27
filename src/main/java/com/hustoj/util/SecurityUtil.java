package com.hustoj.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.regex.Pattern;

public class SecurityUtil {

    // PHP allows: a-z, A-Z, 0-9, -, _, and * only at position 0
    // Java pattern: start with letter or * or -, then any combo of letter/digit/-/_
    private static final Pattern USER_NAME_PATTERN = Pattern.compile("^[*a-zA-Z0-9_-][a-zA-Z0-9_-]*$");
    private static final Pattern BAD_WORDS_PATTERN;

    static {
        // PHP bad_words: 装逼,草泥马,特么的,撕逼,玛拉戈壁,爆菊,JB,呆逼,本屌,齐B短裙,法克鱿,丢你老母,达菲鸡,装13,逼格,蛋疼,傻逼,绿茶婊,你妈的,表砸,屌爆了,买了个婊,已撸,吉跋猫,妈蛋,逗比,我靠,碧莲,碧池,然并卵,日了狗,吃翔,XX狗,淫家,你妹,浮尸国,滚粗
        String[] badWords = {"装逼", "草泥马", "特么的", "撕逼", "玛拉戈壁", "爆菊", "JB", "呆逼", "本屌", "齐B短裙",
            "法克鱿", "丢你老母", "达菲鸡", "装13", "逼格", "蛋疼", "傻逼", "绿茶婊", "你妈的", "表砸",
            "屌爆了", "买了个婊", "已撸", "吉跋猫", "妈蛋", "逗比", "我靠", "碧莲", "碧池", "然并卵",
            "日了狗", "吃翔", "XX狗", "淫家", "你妹", "浮尸国", "滚粗", "admin", "root", "administrator", "test"};
        StringBuilder sb = new StringBuilder();
        for (String w : badWords) {
            if (sb.length() > 0) sb.append("|");
            sb.append(Pattern.quote(w));
        }
        BAD_WORDS_PATTERN = Pattern.compile(sb.toString());
    }

    public static String removeXSS(String val) {
        if (val == null) return null;
        val = val.replaceAll("[\\x00-\\x08\\x0b\\x0c\\x0e-\\x19]", "");
        String[] search = {"javascript", "vbscript", "expression", "applet", "meta", "xml", "blink",
            "link", "script", "embed", "object", "frameset", "ilayer", "bgsound"};
        String[] events = {"onabort", "onactivate", "onafterprint", "onafterupdate", "onbeforeactivate",
            "onbeforecopy", "onbeforecut", "onbeforedeactivate", "onbeforeeditfocus", "onbeforepaste",
            "onbeforeprint", "onbeforeunload", "onbeforeupdate", "onblur", "onbounce", "oncellchange",
            "onchange", "onclick", "oncontextmenu", "oncontrolselect", "oncopy", "oncut",
            "ondataavailable", "ondatasetchanged", "ondatasetcomplete", "ondblclick", "ondeactivate",
            "ondrag", "ondragend", "ondragenter", "ondragleave", "ondragover", "ondragstart", "ondrop",
            "onerror", "onerrorupdate", "onfilterchange", "onfinish", "onfocus", "onfocusin", "onfocusout",
            "onhelp", "onkeydown", "onkeypress", "onkeyup", "onlayoutcomplete", "onload", "onlosecapture",
            "onmousedown", "onmouseenter", "onmouseleave", "onmousemove", "onmouseout", "onmouseover",
            "onmouseup", "onmousewheel", "onmove", "onmoveend", "onmovestart", "onpaste",
            "onpropertychange", "onreadystatechange", "onreset", "onresize", "onresizeend",
            "onresizestart", "onrowenter", "onrowexit", "onrowsdelete", "onrowsinserted", "onscroll",
            "onselect", "onselectionchange", "onselectstart", "onstart", "onstop", "onsubmit", "onunload"};
        for (String s : search) {
            val = val.replaceAll("(?i)" + s, "");
        }
        for (String e : events) {
            val = val.replaceAll("(?i)" + e + "=", "");
        }
        return val;
    }

    public static String escapeHtml(String str) {
        if (str == null) return "";
        return str.replace("&", "&amp;")
                  .replace("<", "&lt;")
                  .replace(">", "&gt;")
                  .replace("\"", "&quot;")
                  .replace("'", "&#x27;");
    }

    public static boolean isValidUserName(String userName) {
        if (userName == null || userName.isEmpty()) return false;
        if (userName.length() > 48) return false;
        return USER_NAME_PATTERN.matcher(userName).matches();
    }

    public static boolean hasBadWords(String text) {
        if (text == null) return false;
        return BAD_WORDS_PATTERN.matcher(text).find();
    }

    // PHP's too_simple(): returns true if password is too simple
    // Conditions: length>=8, has digit, has uppercase, has lowercase, has special char
    // If conditions met < 2, password is considered too simple (weak)
    public static boolean isPasswordStrongEnough(String password) {
        if (password == null) return false;
        int conditionsMet = 0;
        // Length >= 8
        if (password.length() >= 8) conditionsMet++;
        // Has digit
        if (password.matches(".*\\d.*")) conditionsMet++;
        // Has uppercase
        if (password.matches(".*[A-Z].*")) conditionsMet++;
        // Has lowercase
        if (password.matches(".*[a-z].*")) conditionsMet++;
        // Has special char
        if (password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) conditionsMet++;
        // too_simple = conditionsMet < 2, so isPasswordStrongEnough = conditionsMet >= 2
        return conditionsMet >= 2;
    }

    public static String generateToken() {
        return generateToken(32);
    }

    public static String generateToken(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String pwGen(String password, boolean md5ed) {
        if (!md5ed) password = md5(password);
        String salt = sha1(String.valueOf(new SecureRandom().nextInt()));
        salt = salt.substring(0, 4);
        try {
            MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
            // PHP: sha1($password . $salt) where salt is hex chars (e.g. "5de9")
            // Result: 20 raw bytes = SHA-1 hash of (password_hex . salt_chars)
            byte[] hash = sha1.digest((password + salt).getBytes(java.nio.charset.StandardCharsets.ISO_8859_1));
            // Append salt CHARS (as ISO-8859-1 bytes) to hash bytes
            byte[] result = new byte[hash.length + salt.length()];
            System.arraycopy(hash, 0, result, 0, hash.length);
            for (int i = 0; i < salt.length(); i++) {
                result[hash.length + i] = (byte) salt.charAt(i);
            }
            return Base64.getEncoder().encodeToString(result);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean pwCheck(String password, String saved) {
        if (isOldPW(saved)) {
            String mpw = isOldPW(password) ? password : md5(password);
            return mpw.equals(saved);
        }
        byte[] svd;
        try {
            svd = Base64.getDecoder().decode(saved);
        } catch (Exception e) {
            return false;
        }
        if (svd.length < 24) return false;
        String salt = new String(svd, 20, 4);
        String hash;
        try {
            String pwdHex = isOldPW(password) ? password : md5(password);
            byte[] hashBytes = MessageDigest.getInstance("SHA-1").digest(
                (pwdHex + salt).getBytes(java.nio.charset.StandardCharsets.ISO_8859_1));
            byte[] result = new byte[hashBytes.length + salt.length()];
            System.arraycopy(hashBytes, 0, result, 0, hashBytes.length);
            for (int i = 0; i < salt.length(); i++) {
                result[hashBytes.length + i] = (byte) salt.charAt(i);
            }
            hash = Base64.getEncoder().encodeToString(result);
        } catch (java.security.NoSuchAlgorithmException e) {
            return false;
        }
        return hash.equals(saved);
    }

    public static boolean isOldPW(String password) {
        if (password == null || password.length() != 32) return false;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (('0' <= c && c <= '9') || ('a' <= c && c <= 'f') || ('A' <= c && c <= 'F')) continue;
            return false;
        }
        return true;
    }

    private static String sha1(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getSafeZipPath(String baseDir, String entryName) {
        if (baseDir == null || entryName == null) throw new IllegalArgumentException("path is null");
        String sep = System.getProperty("file.separator");
        entryName = entryName.replace("\\", sep).replace("/", sep);
        String[] parts = entryName.split(sep);
        StringBuilder safeRelative = new StringBuilder();
        for (String part : parts) {
            if (part.isEmpty() || part.equals(".")) continue;
            if (part.equals("..")) {
                int lastSep = safeRelative.lastIndexOf(sep);
                if (lastSep > 0) safeRelative.delete(lastSep, safeRelative.length());
            } else {
                if (safeRelative.length() > 0) safeRelative.append(sep);
                safeRelative.append(part);
            }
        }
        String realBase;
        try {
            java.io.File f = new java.io.File(baseDir);
            realBase = f.getCanonicalPath();
        } catch (java.io.IOException e) {
            realBase = baseDir;
        }
        String finalPath = realBase + sep + safeRelative;
        java.io.File finalFile = new java.io.File(finalPath);
        String finalCanonical;
        try {
            finalCanonical = finalFile.getCanonicalPath();
        } catch (java.io.IOException e) {
            throw new SecurityException("Path traversal detected: " + entryName);
        }
        if (!finalCanonical.startsWith(realBase + sep) && !finalCanonical.equals(realBase)) {
            throw new SecurityException("Path traversal detected: " + entryName);
        }
        return finalCanonical;
    }
}
