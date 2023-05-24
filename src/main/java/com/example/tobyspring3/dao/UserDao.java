package com.example.tobyspring3.dao;
import com.example.tobyspring3.domain.User;

import java.sql.*;
import java.util.Map;

import static java.lang.System.getenv;

public class UserDao {
    private ConnectionMaker cm;

    public UserDao(ConnectionMaker connectionMaker) {
        cm = new DConnectionMaker();
    }

    private static void close(Connection conn, PreparedStatement pstmt) throws SQLException {
        pstmt.close();
        conn.close();
    }


    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection conn = cm.makeConnection();

        PreparedStatement pstmt = conn.prepareStatement("insert into user(id, name, password) values(?, ?, ?)");
        pstmt.setString(1,user.getId());
        pstmt.setString(2,user.getName());
        pstmt.setString(3,user.getPassword());

        pstmt.executeUpdate();
        close(conn, pstmt);
    }



    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection conn = cm.makeConnection();
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
        ConnectionMaker cm = new DConnectionMaker();
        UserDao userDao = new UserDao(cm);
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
