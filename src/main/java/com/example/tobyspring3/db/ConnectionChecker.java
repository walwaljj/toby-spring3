package com.example.tobyspring3.db;

import java.sql.*;
import java.util.Map;

import static java.lang.System.getenv;

public class ConnectionChecker {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        ConnectionChecker cc = new ConnectionChecker();
//        cc.check();
//        cc.add();
        cc.select();

    }

    public void add() throws ClassNotFoundException, SQLException {
        Map<String, String> env = getenv();
        String dbHost = env.get("DB_HOST");
        String dbUser = env.get("DB_HOST");
        String dbPassword = env.get("DB_HOST");
        Class.forName("com.mysql.cj.jdbc.Driver"); // 해당 경로의 드라이버를 쓰겠다.
        Connection conn = DriverManager.getConnection(dbHost, dbUser, dbPassword); // DB에 접속을 하겠다.
        PreparedStatement pstmt = conn.prepareStatement("insert into user(name,password)  values (?,?)");
        pstmt.setString(1, "dd");
        pstmt.setString(2, "12345678");
        pstmt.executeUpdate();
    }

    private void check() throws SQLException, ClassNotFoundException {
        Map<String, String> env = getenv();
        String dbHost = env.get("DB_HOST");
        String dbUser = env.get("DB_HOST");
        String dbPassword = env.get("DB_HOST");
        Class.forName("com.mysql.cj.jdbc.Driver"); // 해당 경로의 드라이버를 쓰겠다.
        Connection conn = DriverManager.getConnection(dbHost, dbUser, dbPassword); // DB에 접속을 하겠다.

        Statement stmt = conn.createStatement(); //
        ResultSet rs = stmt.executeQuery("show databases"); // 쿼리를 이용해 결과를 받겠다.
//        rs = stmt.getResultSet();
        while (rs.next()){
            String line = rs.getString(1);
            System.out.println(line);
        }
    }

    public void select() throws SQLException, ClassNotFoundException {
        Map<String, String> env = getenv();
        String dbHost = env.get("DB_HOST");
        String dbUser = env.get("DB_HOST");
        String dbPassword = env.get("DB_HOST");
        Class.forName("com.mysql.cj.jdbc.Driver"); // 해당 경로의 드라이버를 쓰겠다.
        Connection conn = DriverManager.getConnection(dbHost, dbUser, dbPassword); // DB에 접속을 하겠다.
        Statement stmt = conn.createStatement(); //
        ResultSet rs = stmt.executeQuery("select * from user"); // 쿼리를 이용해 결과를 받겠다.
//        rs = stmt.getResultSet();
        while (rs.next()){
            int id = rs.getInt(1);
            String name = rs.getString(2);
            String password = rs.getString(3);
            System.out.printf("%d %s %s\n",id,name,password);
        }
    }
}
