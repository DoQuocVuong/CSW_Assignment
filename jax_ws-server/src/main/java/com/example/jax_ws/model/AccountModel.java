package com.example.jax_ws.model;

import com.example.jax_ws.entity.Account;
import com.example.jax_ws.utils.ConnectionHelper;

import java.net.ConnectException;
import java.sql.*;

public class AccountModel {
    private Connection conn;

    public AccountModel() {
        conn = ConnectionHelper.getConnection();
    }
    public static Account findById(int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("select * from accounts where id = ?");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String password = rs.getString("password");
            String name = rs.getString("name");
            String email = rs.getString("email");
            return new Account(password, name, email);
        }
        return null;
    }

    public Account findByEmail(String email) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("select * from accounts where email = ?");
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String password = rs.getString("password");
            String name = rs.getString("name");
            email = rs.getString("email");
            return new Account(password, name, email);
        }
        return null;
    }

    public Account save(Account account) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("insert into accounts (password, name, email) values ( ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, account.getEmail());
        stmt.setString(2, account.getName());
        stmt.setString(3, account.getPassword());
        int affectedRows = stmt.executeUpdate();
        if (affectedRows > 0) {
            ResultSet resultSetGeneratedKeys = stmt.getGeneratedKeys();
            if (resultSetGeneratedKeys.next()) {
                int id = resultSetGeneratedKeys.getInt(1);
                account.setId(id);
                return account;
            }
        }

        return null;
    }
}
