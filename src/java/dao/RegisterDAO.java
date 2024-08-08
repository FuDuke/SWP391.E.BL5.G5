package dao;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.User;

public class RegisterDAO extends DBContext {
    
    public int addUser(User user, int userID) {
        int n = 0;
        String sql = "INSERT INTO [dbo].[user]\n"
                + "           ([userID]\n"
                + "           ,[userName]\n"
                + "           ,[userGender]\n"
                + "           ,[userBirth]\n"
                + "           ,[userAddress]\n"
                + "           ,[userPhone]\n"
                + "           ,[userAvatar])\n"
                + "     VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, userID);
            pre.setString(2, user.getUserName());
            pre.setString(3, user.getUserGender());
            pre.setString(4, user.getUserBirth());
            pre.setString(5, user.getUserAddress());
            pre.setString(6, user.getUserPhone());
            pre.setString(7, user.getUserAvatar());
            n = pre.executeUpdate();
        } catch (SQLException ex) {

        }
        return n;
    }
    
     public int addAccount(Account account) {
        int userID = -1;
        String sql = "INSERT INTO [dbo].[account]\n"
                + "           ([userMail]\n"
                + "           ,[userPassword]\n"
                + "           ,[userRole])\n"
                + "     VALUES (?,?,?)";
        try {
            PreparedStatement pre = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pre.setString(1, account.getUserMail());
            pre.setString(2, account.getUserPassword());
            pre.setInt(3, account.getUserRole());
            int rows = pre.executeUpdate();

            if (rows > 0) {
                try (ResultSet rs = pre.getGeneratedKeys()) {
                    if (rs.next()) {
                        userID = rs.getInt(1);
                    }
                }
            }
        } catch (SQLException ex) {

        }
        return userID;
    }
    public List<Account> getListAccount() {
        List<Account> listAccount = new ArrayList<>();
        String sql = "select * from account";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int userID = rs.getInt("userID");
                String userMail = rs.getString("userMail");
                String userPassword = rs.getString("userPassword");
                int userRole = rs.getInt("userRole");
                
                Account account = new Account(userID, userMail, userPassword, userRole);
                listAccount.add(account);
            }
        } catch (SQLException ex) {

        }
        return listAccount;
    }     
}