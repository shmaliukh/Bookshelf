package org.vshmaliukh.services.file_service.sqllite;

import lombok.extern.slf4j.Slf4j;
import org.vshmaliukh.shelf.literature_items.Item;

import java.sql.*;
import java.util.List;
import java.util.Map;

@Slf4j
public final class SqlLiteUtils {

    private SqlLiteUtils(){}

    public static boolean createNewDatabase(String url) { // TODO should all methods return boolean (?)
        try {
            Connection conn = DriverManager.getConnection(url);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                log.info("The driver name is " + meta.getDriverName());
                log.info("A new database '" + url + "' has been created.");
                return true;
            }

        } catch (SQLException sqle) {
            log.error(sqle.getMessage());
        }
        return false;
    }

    public static void connect(String url) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            log.info("Connection to SQLite has been established.");
        } catch (SQLException sqle) {
            log.error(sqle.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException sqle) {
                log.error(sqle.getMessage());
            }
        }
    }

    public static void createNewTable(String url, String sql) {
        try{
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException sqle) {
            log.error(sqle.getMessage());
        }
    }

    public static void insert(String url, String sql,List<String> parameterList, Map<String, String> parameterValueMap) {
        try{
            Connection conn = null;
            try {
                conn = DriverManager.getConnection(url);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            PreparedStatement pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < parameterList.size(); i++) {
                String parameter = parameterList.get(i);
                String value = parameterValueMap.get(parameter);
                pstmt.setString(i + 1, value);
            }
            pstmt.executeUpdate();
        } catch (SQLException sqle) {
            log.error(sqle.getMessage());
        }
    }
}
