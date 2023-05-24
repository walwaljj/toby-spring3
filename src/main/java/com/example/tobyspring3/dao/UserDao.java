package com.example.tobyspring3.dao;
import com.example.tobyspring3.domain.User;

import java.sql.*;
import java.util.Map;

import static java.lang.System.getenv;

public class UserDao {
    private static void close(Connection conn, PreparedStatement pstmt) throws SQLException {
        pstmt.close();
        conn.close();
    }

    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        Map<String,String> env = getenv();
        String dbHost = env.get("DB_HOST");
        String dbUser = env.get("DB_USER");
        String dbPassword = env.get("DB_PASSWORD");
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(
                dbHost, dbUser, dbPassword
        ); // DB에 접속을 하겠다.
        return conn;
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection conn = getConnection();

        PreparedStatement pstmt = conn.prepareStatement("insert into user(id, name, password) values(?, ?, ?)");
        pstmt.setString(1,user.getId());
        pstmt.setString(2,user.getName());
        pstmt.setString(3,user.getPassword());

        pstmt.executeUpdate();
        close(conn, pstmt);
    }



    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement("select id, name, password from user where id = ? ");
        pstmt.setString(1,id);
        ResultSet rs = pstmt.executeQuery();
        rs.next();

        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
        rs.close();

        close(conn, pstmt);
        return user;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao();
        User user = new User();
        user.setId("3");
        user.setName("삼삼");
        user.setPassword("3333");
//        userDao.add(user);

        User selectedUser = userDao.get("3");
        System.out.println(selectedUser.getId());
        System.out.println(selectedUser.getName());
        System.out.println(selectedUser.getPassword());
    }
}