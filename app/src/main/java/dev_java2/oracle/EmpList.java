package dev_java2.oracle;

import dev_java2.util.DBConnectionMgr;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
//모든 RDBMS에서 제공되는 타입이 아니라 오라클에서만 제공하는 타입-sys_refactor

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmpList {
    Connection con = null;//인터페이스-비벼지는부분?
    CallableStatement cstmt=null;//프로시저를 요청할때 사용하는 인터페이스
    OracleCallableStatement osctmt=null;
    ResultSet rs = null;//오라클 커서를 조작하는데 필요한 추상메소드가짐
    DBConnectionMgr dbMgr = new DBConnectionMgr("sco" +
            "tt", "tiger");
    public List<Map<String,Object>> getEmpList() {
        List<Map<String, Object>> list = new ArrayList<>();
        //구글링한 소스를 어디에 붙여야하나? 기준, 선택할 수 있다.-APi를 많이 보고 실습 코드에 적용을 반드시 해봐

        try {
//            con = dbMgr.getConnection();//물리적을 떨어지있는 통로확보

            con = dbMgr.getConnection("scott","tiger");//물리적을 떨어지있는 통로확보
            cstmt = con.prepareCall("{call my_proc(?)}");
            cstmt.registerOutParameter(1, OracleTypes.CURSOR);
            cstmt.execute();
            //굳이 추가로 제공되는 클래스로 치환하는것은 Resultset을 주입받는 메소드 호출이 필요하기때문에
            //CallableStatement는 getCursor를 지원 안해서 삽임함
            osctmt=(OracleCallableStatement) cstmt;
            rs=osctmt.getCursor(1);//커서를 움직일수있다.
            Map<String,Object> rmap=null;
            while (rs.next()) {
                rmap=new HashMap<>();
                rmap.put("empno", rs.getInt(1));//키값, 몇번째자리
                rmap.put("deptno", rs.getInt(2));
                rmap.put("ename", rs.getString(3));
                //List에 Map추가하기
                //List에 튜플이 추가
                //Map에 추가되는건 컬럼
                list.add(rmap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    //프로시저안에서는 여러가지의 DML문을 한번 서버에 접속한 상태에서
    //한번에 처리가능
    //commit, rollback가능함
    //자바로 꺼내서 처리하지 않고 프로시저 내부에서 비즈니스로직(업무포함 프로세스)
    //처리가능-변수활용과 제어문 사용
    //프로시저내부에서 자바의 힘을 빌리지 않고도 처리할 수 있는 프로세스들 있다.
    //시스템의 부하를 줄인다

    public static void main(String[] args) {
        EmpList eList=new EmpList();
        List<Map<String, Object>> list=eList.getEmpList();
        System.out.println(list);
    }
}
