package org.vshmaliukh.services.file_service.sqllite;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;

@Slf4j
public final class SqlLiteUtils {

    private SqlLiteUtils(){}

    public static boolean createNewDatabase(String url) { // TODO should all methods return boolean
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
        sql = "CREATE TABLE IF NOT EXISTS " + sql; // TODO
        //String sql = "CREATE TABLE IF NOT EXISTS employees (\n"
        //        + " id integer PRIMARY KEY,\n"
        //        + " name text NOT NULL,\n"
        //        + " capacity real\n"
        //        + ");";

        try{
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException sqle) {
            log.error(sqle.getMessage());
        }
    }

    public void insert(String url, String name, double capacity) {
        String sql = "INSERT INTO employees(name, capacity) VALUES(?,?)";

        try{
            Connection conn = null;
            try {
                conn = DriverManager.getConnection(url);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setDouble(2, capacity);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
