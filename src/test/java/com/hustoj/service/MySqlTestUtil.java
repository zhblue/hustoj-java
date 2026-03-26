package com.hustoj.service;

import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;
import com.mysql.cj.jdbc.MysqlDataSource;

public class MySqlTestUtil {
    public static DataSource createDataSource() {
        MysqlDataSource ds = new MysqlDataSource();
        try {
            ds.setServerName("localhost");
            ds.setPort(3306);
            ds.setDatabaseName("jol");
            ds.setUser("hustoj");
            ds.setPassword("OostHD0YYXi2UfY2AsmKONCBJnHC6B");
            ds.setUseSSL(false);
            ds.setAllowPublicKeyRetrieval(true);
            ds.setServerTimezone("Asia/Shanghai");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ds;
    }

    public static void cleanTables(JdbcTemplate jdbc, String... tables) {
        jdbc.execute("SET FOREIGN_KEY_CHECKS=0");
        for (String table : tables) {
            try {
                jdbc.execute("TRUNCATE TABLE " + table);
            } catch (Exception e) {
                // Table might not exist or be empty
            }
        }
        jdbc.execute("SET FOREIGN_KEY_CHECKS=1");
    }

    public static JdbcTemplate createJdbc() {
        return new JdbcTemplate(createDataSource());
    }
}
