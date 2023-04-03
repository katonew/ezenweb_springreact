package ezenweb.example.day01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Dao {
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    public Dao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springweb","root","1234");
            System.out.println("연동성공");
        }catch (Exception e){
            System.out.println("연동실패 : "+ e);
        }

    } // dao e

    // 1. DB에 추가
    public boolean setmember(LombokDto dto){
        String sql = "insert into testmember values (?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1,dto.getMno());
            ps.setString(2,dto.getMid());
            ps.setString(3,dto.getMpassword());
            ps.setLong(4, dto.getPoint());
            ps.setString(5,dto.getPhone());
            int count = ps.executeUpdate();
            if(count==1){return true; }
        }catch (Exception e){ System.out.println("setmember 오류 :" + e);}
        return false;
    }



} // class e
