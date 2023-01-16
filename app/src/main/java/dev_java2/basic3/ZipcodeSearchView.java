package dev_java2.basic3;

import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import dev_java2.util.DBConnectionMgr;

public class ZipcodeSearchView extends JFrame implements ItemListener,FocusListener, ActionListener, MouseListener {
    // 선언부
    // 사용자가 선택한 zdo
    String zdo = null;
    // 사용자가 선택한 sigu
    String sigu = null;
    // 시용자가 선택한 dong
    String dong = null;
    // 콤보에는 배열이 들어가더라
    // DB에서 가져온 zdos[]
    String[] zdos = null;
    // DB에서 가져온 sigus[]
    String[] sigus = null;
    // DB에서 가져온 dongs[]
    String[] dongs = null;
    // 중분류(sigu), 소분류(dong)
    String[] totals = new String[] { "전체" };
    // 북쪽에 붙일 속지-콤보박스 3개-JTextField, JButton
    // FlowLayout-배치-중앙에서 좌우로 펼쳐지면서 배치가됨
    // JPanel jp_north = new JPanel();//디폴트 레이아웃-FlowLayout
    JPanel jp_north = new JPanel(new FlowLayout(FlowLayout.LEFT));// 왼쪽 쏠리는 레이아웃
    JComboBox jcb_zdo = null;
    JComboBox jcb_sigu = null;
    JComboBox jcb_dong = null;
    ///////////////////////////////// [[[[[DB연동
    ///////////////////////////////// 시작]]]]]]////////////////////////////////////////
    // 동이름을 입력받는 텍스트 필드와 조회버튼 추가
    // 물리적으로 떨어져 있는 오라클 서버에 접속하는데 필요한 공통 코드작성
    DBConnectionMgr dbMgr = new DBConnectionMgr();
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    ////////////////////////////////////////////// [연동끝]///////////////////////////

    // 동이름을 입력받는 텍스트 필드와 조회버튼 추가
    // 생성자 파라미터자리를 이용하면 추가적인 메소드 호출 없이도 해당 화면에 대한 추가적인 초기화
    // 이른 인스턴스화이다.
    JTextField jtf_search = new JTextField("동이름을 입력하세요", 20);
    JButton jbtn_search = new JButton("조회");
    // 테이블 생성
    String[] cols = { "우편번호", "주소" };
    String[][] data = new String[3][3];
    DefaultTableModel dtm_zipcode = new DefaultTableModel(data, cols);
    JTable jtb_zipcode = new JTable(dtm_zipcode);
    JTableHeader jth_zipcode = jtb_zipcode.getTableHeader();
    JScrollPane jsp_zipcode = new JScrollPane(jtb_zipcode, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    MemberShip memberShip=null;

    // 생성자
    public ZipcodeSearchView() {
        jbtn_search.addActionListener(this);//조회버튼누르면 실행
        jtf_search.addFocusListener(this);
        jtf_search.addActionListener(this);//검색창에 넣고 엔터누르면 실행
        zdos = getZDOList();
        jcb_zdo = new JComboBox(zdos);
        jcb_zdo.addItemListener(this);
        jcb_sigu = new JComboBox(totals);
        jcb_sigu.addItemListener(this);
        jcb_dong = new JComboBox(totals);
        jcb_dong.addItemListener(this);

    }
    public ZipcodeSearchView(MemberShip memberShip){
        //this 뒤에 괄호있으면 자기자신의 디폴트 생성자 호출
        this();//나자신의 디폴트 생성자 호출-81~92번 진행되어야함
        this.memberShip = memberShip;
        this.initDisplay();
    }

    // 대분류 정보 초기화에 필요한 DB조회하기 구현
    public String[] getZDOList() {
        StringBuilder sql = new StringBuilder();
        sql.append("select '전체' zdo from dual ");
        sql.append(" union all                  ");
        sql.append(" select  zdo                ");
        sql.append(" from (         ");
        sql.append("   select distinct(zdo) zdo");
        sql.append("   from zipcode_t  ");
        sql.append("   order by zdo asc)");
        try {
            // con의 주소번지가 확인 되면 오라클 서버와 연결통로가 확보되었다.
            con = dbMgr.getConnection();
            // 전령의 역할. 정적-스테이트먼트 동적-프리페어드스테이트먼트
            pstmt = con.prepareStatement(sql.toString());
            // 오라클에서 생성된 테이블의 커서 디폴트위치는 항상 isTop이다.커서가 존재하는데...이동시킴
            rs = pstmt.executeQuery();
            Vector<String> v = new Vector<>();
            // rs.previous(); ???
            while (rs.next()) {
                String zdo = rs.getString("zdo");
                v.add(zdo);
            }
            zdos = new String[v.size()];
            v.copyInto(zdos);
        } catch (SQLException se) {
            System.out.println(se.toString());// getMessage();도 가능
            System.out.println(sql.toString());// 위에서 쓴 쿼리문 프린트
        } finally {
            // 사용한 자원 반납하기-생성의 역순으로 할것
            // 생략해도 언젠가 반납은 이루어짐-명시적으로 반납처리 권장함
            // 왜냐하면 오라클 서버에서 커넥션을 강제로 종료시켜버리니까...
            try {
                dbMgr.freeConnection(con, pstmt, rs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return zdos;
    }// end of getZDOList

    public String[] getSiguList(String zdo) {
        StringBuilder sql = new StringBuilder();
        sql.append("select '전체' sigu from dual ");
        sql.append(" union all                  ");
        sql.append(" select  sigu                ");
        sql.append(" from (         ");
        sql.append("   select distinct(sigu) sigu");
        sql.append("   from zipcode_t  ");
        sql.append("   where zdo=?  "); // 오라클에서 :zdo가 ?변수됨
        sql.append("   order by sigu asc)");
        try {
            // con의 주소번지가 확인 되면 오라클 서버와 연결통로가 확보되었다.
            con = dbMgr.getConnection();
            pstmt = con.prepareStatement(sql.toString());
            pstmt.setString(1, zdo);
            // 오라클에서 생성된 테이블의 커서 디폴트위치는 항상 isTop이다.커서가 존재하는데...이동시킴
            rs = pstmt.executeQuery();
            Vector<String> v = new Vector<>();// coptInto메소드 사용 위해서 그대로 복사하려고
            // rs.previous(); ???
            while (rs.next()) {
                String sigu = rs.getString("sigu");
                v.add(sigu);
            }
            // 시구 콤보박스에 들어갈 배열 생성하기
            sigus = new String[v.size()];
            // 벡터에 들어있는 값 스트링 배열에 복사하기
            v.copyInto(sigus);
        } catch (SQLException se) {
            System.out.println(se.toString());// getMessage();도 가능
            System.out.println(sql.toString());// 위에서 쓴 쿼리문 프린트
        } finally {
            // 사용한 자원 반납하기-생성의 역순으로 할것
            // 생략해도 언젠가 반납은 이루어짐-명시적으로 반납처리 권장함
            // 왜냐하면 오라클 서버에서 커넥션을 강제로 종료시켜버리니까...
            try {
                dbMgr.freeConnection(con, pstmt, rs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sigus;
    }

    // public String[] getDongList(String zdo, String sigu) {

    // return dong;
    // }

    // 화면처리부
    public void initDisplay() {
        jtb_zipcode.addMouseListener(this);
        jth_zipcode.setBackground(Color.CYAN);
        jth_zipcode.setFont(new Font("맑은고딕", Font.BOLD, 20));
        jtb_zipcode.getColumnModel().getColumn(0).setPreferredWidth(100);
        jtb_zipcode.getColumnModel().getColumn(1).setPreferredWidth(300);
        jtb_zipcode.setGridColor(Color.BLACK);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jp_north.add(jcb_zdo);
        jp_north.add(jcb_sigu);
        jp_north.add(jcb_dong);
        jp_north.add(jtf_search);
        jp_north.add(jbtn_search);

        this.add("North", jp_north);
        this.add("Center", jsp_zipcode);
        this.setSize(630, 400);
        this.setVisible(false);
    }

    // 디비에 뿌리기...
    public void refreshData(String zdo, String dong) {
        System.out.println("refreshData 호출 성공");
        StringBuilder sql = new StringBuilder();
        sql.append("select ");
        sql.append("       zipcode, address");
        sql.append("   from zipcode_t");
        sql.append(" where 1=1");// 조건이 들어가야함 IF문 안에넣기
        if (zdo != null && zdo.length() > 0) { // 빈 문자열이 아닌것도 필터링
            sql.append(" and zdo=?");// :zdo 물음표로 바꾸기
        } // zdo가 존재할 때만
        if (dong != null && dong.length() > 0) { // && : and
            sql.append(" and dong like ? || '%'");// :x를 ?로 바꾸기
        } // 동을 입력 받았을 때만
        int i = 1; // 순서때문에
        try {
            con = dbMgr.getConnection();// 오라클 서버와 연결
            // 들어있는 타입과 형전환이 잘못 선택 되면 ClassCastingException이 발생가능
            pstmt = con.prepareStatement(sql.toString());// 들어있는타입이 String이니까
            if (zdo != null && zdo.length() > 0) {
                pstmt.setString(i++, zdo);
            }
            if (dong != null && dong.length() > 0) {
                pstmt.setString(i++, dong);
            }
            //입력, 수정 , 삭제인경우에는 executeUpdate()를 사용하고 리턴타입은 int
            //조회인경우에는 executeQuery()를 사용하고 리턴타입은 ResultSet
            //테이블을 생성할 때에는 execute()를 사용함
            //업무가 바뀌더라도 변하는건 테이블 명과 컬럼명만 변함-다른건 그래로 재사용됨->ORM솔루션->JPA기술 출현, 활용
            //JPA(Hibernate-쿼리문이 업다.) 기술 출현, 활용
            rs=pstmt.executeQuery();
            List<Map<String, Object>> zipList=new ArrayList<>();
            Map<String, Object> rmap=null;
            while(rs.next()){
                rmap=new HashMap<>();
                rmap.put("zipcode", rs.getString("zipcode"));
                rmap.put("address", rs.getString("address"));
                zipList.add(rmap);
            }
            System.out.println(zipList);
            //화면 처리하기-테이블 새로고침하기
            //조회결과가 있니?
            if(zipList.size()>0){
                //조회를 연속하여 요청할경우 기존에 조회된 화면은 지워주라
                while (dtm_zipcode.getRowCount()>0){//JTable은 양식일 뿐이고 실제 데이터는 DefaultTableModel
                    dtm_zipcode.removeRow(0);
                }//end of while
                //새로 조회된 결과 출력하기
                for(int x=0; x<zipList.size(); x++){
                    Map<String,Object> rmap2=zipList.get(x);
                    Vector<String> oneRow=new Vector<>();
                    oneRow.add(0,rmap2.get("zipcode").toString());
                    oneRow.add(1,rmap2.get("address").toString());
                    dtm_zipcode.addRow(oneRow);
                }//end of for
            }
        } catch (SQLException se) {
            System.out.println(se.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 사용한 자원을 명시적으로 반드시 반납할것 - 안하면 JVM이 언젠가는 해줌
            // 그 시간을 앞당기는 코드임
            dbMgr.freeConnection(con, pstmt, rs);
        }

    }//end of refreshData

    // 메인메소드
    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        ZipcodeSearchView zcsv = new ZipcodeSearchView();
        zcsv.initDisplay();

    }

    @Override
    public void itemStateChanged(ItemEvent ie) {
        // 이벤트가 감지되는 소스 가져오기
        Object obj = ie.getSource();
        // 너 ZDO콤보박스니??
        if (obj == jcb_zdo) {
            if (ie.getStateChange() == ItemEvent.SELECTED) {
                System.out.println("선택한 ZDO===>" + zdos[jcb_zdo.getSelectedIndex()]);
                zdo = zdos[jcb_zdo.getSelectedIndex()];
                sigus = getSiguList(zdo);
                // 대분류가 결정이 되었을때 sigus를 초기화 해줘야 한다.
                // 기존에 디폴트로 전체 상수값을 넣어 두었으니 이것을 삭제하고
                jcb_sigu.removeAllItems();// 아까 토탈 넣어놓은거 지워줌
                // 새로운 DB서버에서 읽어온 값으로 아이템을 추가 해야 한다.-초기화
                for (int i = 0; i < sigus.length; i++) {
                    jcb_sigu.addItem(sigus[i]);

                }
            }
        }

    }

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource() == jtf_search) {
            jtf_search.setText("");
        }

    }

    // 아래의 메소드는 구현 할 필요가 없지만 지우면 에러가 발생 한다. 왜냐면 추상 메소드이니까.
    // 인터페이스를 임플리먼츠하면 반드시 구현체 클래스가 되어야하므로
    // 인터페이스가 소지한 모든 추상메소드를 구현해야한다.
    @Override
    public void focusLost(FocusEvent e) {// 구현으로 본다 {} 있으니까.

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if(jbtn_search==obj || jtf_search==obj ){
            String myDong=jtf_search.getText();
            refreshData(zdo,myDong);
        }
    }

    //마우스 하나만 써도 5개 다 있어야한다.
    @Override
    public void mouseClicked(MouseEvent e) {
//        if(e.getClickCount()==2){
//            System.out.println("더블클릭 한거야?");
//        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e){
        if(e.getClickCount() == 2) {
            //JTable에서 선택한 로우의 index값을 담기
            int index = jtb_zipcode.getSelectedRow();
            for(int i = 0; i < dtm_zipcode.getRowCount(); i++) {
                if(jtb_zipcode.isRowSelected(i)) {
                    String address = dtm_zipcode.getValueAt(i, 1).toString();
                    memberShip.jtf_zipcode.setText(String.valueOf(dtm_zipcode.getValueAt(i, 0)));
                    memberShip.jtf_address.setText(address);
                    this.dispose();
                }
            }
        }//더블 클릭 했을때
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
