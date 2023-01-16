package dev_java2.oracle;

import dev_java2.util.DBConnectionMgr;

import java.sql.CallableStatement;
import java.sql.Connection;

//클래스 설계에 있어서 DAO패턴의 사용은 필수가 되었다.
//특히 MyBatis와 같은 ORM솔루셩리 제공되면서 더 강조되었다.
//JDBC API-> MyBatis->JPA(Hibernate)-쿼리문이 없다.(추상적이다. 조인관계는 어떡하지?)
public class ChatDao {
    Connection con = null;//인터페이스-비벼지는부분?
    CallableStatement cstmt=null;
    DBConnectionMgr dbMgr = new DBConnectionMgr("kiwi", "tiger");
    public String login(String mem_id, String mem_pw){
        String mem_name =null;
        try {
//            con = dbMgr.getConnection();//물리적을 떨어지있는 통로확보

            con = dbMgr.getConnection("kiwi","tiger");//물리적을 떨어지있는 통로확보
            cstmt = con.prepareCall("{call proc_login(?,?,?)}");
            cstmt.setString(1, mem_id);
            cstmt.setString(2, mem_pw);
            cstmt.registerOutParameter(3, java.sql.Types.VARCHAR);
            cstmt.executeUpdate();
            mem_name=cstmt.getString(3);
//            System.out.println(mem_name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mem_name;
        //return "회원가입했던 너의 이름";
    }

    public static void main(String[] args) {
        ChatDao chatDao = new ChatDao();
        String mem_id = chatDao.login("tomato", "123");
        System.out.println(mem_id);
    }

}

