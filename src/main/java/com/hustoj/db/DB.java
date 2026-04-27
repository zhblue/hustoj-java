package com.hustoj.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DB {
    private static HikariDataSource dataSource;
    private static JdbcTemplate jdbcTemplate;
    private static TransactionTemplate transactionTemplate;
    private static Properties config;

    public static final String SESSION_USER_ID = "HUSTOJ_user_id";
    public static final String SESSION_NICK = "HUSTOJ_nick";
    public static final String SESSION_ADministrator = "HUSTOJ_administrator";
    public static final String SESSION_SOURCE_BROWSER = "HUSTOJ_source_browser";
    public static final String SESSION_PROBLEM_EDITOR = "HUSTOJ_problem_editor";
    public static final String SESSION_PROBLEM_VERIFITER = "HUSTOJ_problem_verifiter";
    public static final String SESSION_HTTP_JUDGE = "HUSTOJ_http_judge";
    public static final String SESSION_VCODE = "HUSTOJ_vcode";
    public static final String SESSION_VFAIL = "HUSTOJ_vfail";
    public static final String SESSION_CSRF_TOKEN = "HUSTOJ_csrf_token";

    static {
        loadConfig();
        initDataSource();
    }

    private static void loadConfig() {
        config = new Properties();
        try (InputStream is = DB.class.getClassLoader().getResourceAsStream("oj.properties")) {
            if (is != null) {
                config.load(is);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load oj.properties", e);
        }
    }

    private static void initDataSource() {
        try {
            HikariConfig hikariConfig = new HikariConfig();
            String host = getConfig("db.host", "localhost");
            String dbName = getConfig("db.name", "jol");
            String user = getConfig("db.user", "root");
            String pass = getConfig("db.password", "root");
            String encoding = getConfig("db.charset", "utf8");
            hikariConfig.setJdbcUrl("jdbc:mysql://" + host + ":3306/" + dbName 
                    + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai&characterEncoding=" + encoding);
            hikariConfig.setUsername(user);
            hikariConfig.setPassword(pass);
            hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
            hikariConfig.setMaximumPoolSize(20);
            hikariConfig.setMinimumIdle(5);
            hikariConfig.setIdleTimeout(300000);
            hikariConfig.setConnectionTimeout(20000);
            hikariConfig.setMaxLifetime(1200000);
            hikariConfig.setPoolName("HUSTOJ-HikariPool");
            hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
            hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
            hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            dataSource = new HikariDataSource(hikariConfig);
            jdbcTemplate = new JdbcTemplate(dataSource);
            transactionTemplate = new TransactionTemplate(new DataSourceTransactionManager(dataSource));
        } catch (Exception e) {
            // Defensive: if DB connection fails (e.g., in tests), leave fields null
            System.err.println("Warning: DB init failed - " + e.getMessage());
        }
    }

    public static String getConfig(String key) {
        return config.getProperty(key);
    }

    public static String getConfig(String key, String defaultValue) {
        return config.getProperty(key, defaultValue);
    }

    public static boolean getConfigBool(String key) {
        return "true".equalsIgnoreCase(config.getProperty(key, "false"));
    }

    public static int getConfigInt(String key, int defaultValue) {
        try {
            return Integer.parseInt(config.getProperty(key));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    public static JdbcTemplate getJdbcTemplate() {
        if (jdbcTemplate == null) {
            initDataSource();
        }
        return jdbcTemplate;
    }

    public static TransactionTemplate getTransactionTemplate() {
        return transactionTemplate;
    }

    public static String getOjName() {
        return getConfig("oj.name", "HUSTOJ");
    }

    public static String getOjHome() {
        return getConfig("oj.home", "/");
    }

    public static String getOjData() {
        return getConfig("oj.data", "/home/judge/data");
    }

    public static boolean isOjSim() {
        return getConfigBool("oj.sim");
    }

    public static boolean isOjOnline() {
        return getConfigBool("oj.online");
    }

    public static boolean isOjMail() {
        return getConfigBool("oj.mail");
    }

    public static boolean isOjPrinter() {
        return getConfigBool("oj.printer");
    }

    public static boolean isOjRegister() {
        return getConfigBool("oj.register");
    }

    public static boolean isOjVcode() {
        return getConfigBool("oj.vcode");
    }

    public static boolean isOjPublicStatus() {
        return getConfigBool("oj.public.status");
    }

    public static boolean isOjAutoShare() {
        return getConfigBool("oj.auto.share");
    }

    public static boolean isOjNeedLogin() {
        return getConfigBool("oj.need.login");
    }

    public static boolean isOjOIMode() {
        return getConfigBool("oj.oi.mode");
    }

    public static boolean isOjAppendCode() {
        return getConfigBool("oj.appendcode");
    }

    public static boolean isOjUdp() {
        return getConfigBool("oj.udp");
    }

    public static String getOjUdpServer() {
        return getConfig("oj.udp.server", "127.0.0.1");
    }

    public static int getOjUdpPort() {
        return getConfigInt("oj.udp.port", 1536);
    }

    public static String getOjJudgeHubPath() {
        return getConfig("oj.judge.hub.path", "../judge");
    }

    public static String getOjLang() {
        return getConfig("oj.lang", "cn");
    }

    public static int getOjLangMask() {
        return getConfigInt("oj.lang.mask", 33554356);
    }

    public static String getOjTemplate() {
        return getConfig("oj.template", "syzoj");
    }

    public static String getOjCss() {
        return getConfig("oj.css", "white.css");
    }

    public static String getLoginMod() {
        return getConfig("oj.login.mod", "hustoj");
    }

    public static int getLoginFailLimit() {
        return getConfigInt("oj.login.fail.limit", 5);
    }

    public static boolean isLongLogin() {
        return getConfigBool("oj.long.login");
    }

    public static int getLongLoginTime() {
        return getConfigInt("oj.long.login.time", 86400);
    }

    public static int getSubmitCooldownTime() {
        return getConfigInt("oj.submit.cooldown.time", 10);
    }

    public static int getRankLockDelay() {
        return getConfigInt("oj.rank.lock.delay", 3600);
    }

    public static double getRankLockPercent() {
        return getConfigInt("oj.rank.lock.percent", 0) / 100.0;
    }

    public static int getExpiryDays() {
        return getConfigInt("oj.expiry.days", 365);
    }

    public static String getOjAdmin() {
        return getConfig("oj.admin", "root@localhost");
    }

    public static String getSmtpServer() {
        return getConfig("smtp.server", "smtp.qq.com");
    }

    public static int getSmtpPort() {
        return getConfigInt("smtp.port", 587);
    }

    public static String getSmtpUser() {
        return getConfig("smtp.user", "");
    }

    public static String getSmtpPass() {
        return getConfig("smtp.pass", "");
    }

    public static String[] LANGUAGE_NAMES = {"C", "C++", "Pascal", "Java", "Ruby", "Bash", "Python", "PHP", "Perl", "C#", "Obj-C", "FreeBasic", "Scheme", "Clang", "Clang++", "Lua", "JavaScript", "Go", "SQL", "Fortran", "Matlab", "Cobol", "R", "Scratch3", "Cangjie", "Unknown"};
    public static String[] LANGUAGE_EXT = {"c", "cc", "pas", "java", "rb", "sh", "py", "php", "pl", "cs", "m", "bas", "scm", "c", "cc", "lua", "js", "go", "sql", "f95", "m", "cob", "R", "sb3", "cj", ""};

    public static String[] BALL_COLORS = {"#66cccc", "red", "green", "pink", "yellow", "violet", "magenta", "maroon", "olive", "chocolate"};
    public static String[] BALL_NAMES = {"蒂芙妮蓝", "红", "绿", "粉", "黄", "紫", "洋红", "褐", "橄榄", "巧克力"};

    public static String getLanguageName(int lang) {
        if (lang >= 0 && lang < LANGUAGE_NAMES.length) return LANGUAGE_NAMES[lang];
        return LANGUAGE_NAMES[LANGUAGE_NAMES.length - 1];
    }

    public static String getLanguageExt(int lang) {
        if (lang >= 0 && lang < LANGUAGE_EXT.length) return LANGUAGE_EXT[lang];
        return "";
    }

    public static String[] JUDGE_RESULT_NAMES;
    public static String[] JUDGE_RESULT_COLORS;

    static {
        JUDGE_RESULT_NAMES = new String[]{"Pending", "Pending_Rejudging", "Compiling", "Running_Judging",
            "Accepted", "Presentation_Error", "Wrong_Answer", "Time_Limit_Exceed",
            "Memory_Limit_Exceed", "Output_Limit_Exceed", "Runtime_Error", "Compile_Error",
            "Compile_OK", "Test_Run", "Manual_Confirmation", "Submitting", "Remote_Pending", "Remote_Judging"};
        JUDGE_RESULT_COLORS = new String[]{"label gray", "label label-info", "label label-warning",
            "label label-warning", "label label-success", "label label-danger", "label label-danger",
            "label label-warning", "label label-warning", "label label-warning", "label label-warning",
            "label label-warning", "label label-warning", "label label-info", "label label-success",
            "label label-info", "label label-info", "label label-warning"};
    }

    public static String getResultName(int result) {
        if (result >= 0 && result < JUDGE_RESULT_NAMES.length) return JUDGE_RESULT_NAMES[result];
        return "Unknown";
    }

    public static String getResultColor(int result) {
        if (result >= 0 && result < JUDGE_RESULT_COLORS.length) return JUDGE_RESULT_COLORS[result];
        return "label gray";
    }

    public static void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
