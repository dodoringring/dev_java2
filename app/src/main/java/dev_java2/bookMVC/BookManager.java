package dev_java2.bookMVC;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BookManager extends JFrame implements ActionListener {
    JButton jbtn_sel=new JButton("조회");//select문
    JButton jbtn_ins=new JButton("입력");//insert문
    JButton jbtn_upd=new JButton("수정");//update문
    JButton jbtn_del=new JButton("삭제");//delete문
    JButton jbtn_board=new JButton("게시판");
    JPanel jp_north=new JPanel();
    String gubun = "bookMgr";//도서크루드이면 bMgr로 게시판 CRUD이면 boardMgr


    //개발 방법론에서 디자인패턴중 MVC패턴을 알아보자
    //M : 모델계층
    //XXXDao가 있고 없고는 MVC패턴에 영향이 없다.
    //다만 오라클 서버와 연계에 반복되는 코드를 줄여주고
    // 오픈소스나 라이브러리(iBatis, MyBatis, Hibernate완전자동)를 조립하기위한 요구사항으로 만들어 사용하는 클래스이다.
    //클래스 쪼개기(생성자)-POJO(pure)->Spring(maven)->Spring boot(Gradle)-완결편
    //요구사항으로 만들어 사용하는 클래스임
    //C : 컨트롤계층
    //V : 뷰계층
    //프레임워크를 왜 원하는가?
    //실력차이를 줄여야 함
    //틀이 정해져있다. 클래스 메소드선언 다 되어있다. 그러나 파라미터의 갯수와 타입은 내가 결정한다.
    //개발자는 비즈니스 로직에만 집중해라
    //모델계층에 해당(XXXLogic.java+XXXDao.java)=서비스 계층-비즈니스(업무) 로직 계층
    //전체적인 설계가 목적이다.
    public BookManager() {
//        initDisplay();->속지(JPanel, JScrollPane)로 사용되는 페이지일때
        //요청에 따른 페이지 갱신처리, 화면갱신, 화면초기화
        //그러나 스레드 구현시는 이슈가 발생하니까 주의해야함.
        //문법에러 - 쉬움
        //논리에러잡기는 어렵다.-트러블 슈팅-NullpointerException- 예외사항->500번대 에러
    }
    public void initDisplay() {
        //이벤트 소스와 이벤트 처리 클래스 매핑하기
        jbtn_sel.addActionListener(this);
        jbtn_ins.addActionListener(this);
        jbtn_upd.addActionListener(this);
        jbtn_del.addActionListener(this);
        jbtn_board.addActionListener(this);
        this.add(jp_north);
        jp_north.add(jbtn_sel);
        jp_north.add(jbtn_ins);
        jp_north.add(jbtn_upd);
        jp_north.add(jbtn_del);
        jp_north.add(jbtn_board);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("도서관리 시스템 Ver1.0");
        this.setLocation(100,100);
      this.setSize(500,400);
      this.setVisible(true);
    }

    public static void main(String[] args) {
        BookManager bookmgr=new BookManager();
        //메인스레드와 Runnable을 통해서 구현하는 스레드를 분리할수있는 경우에 사용함
        //메인스레드와 자기자신이 run메소드의 구현체 클래스의 역할을 병행하는 컨셉일때
        //이렇게 안하면 지연이 발생한다. 화면안뜸 소켓 accept가 안일어남, 죽은거
        bookmgr.initDisplay();//refactoring-
    }
    //왜 오버라이드로 재정의 하는건가? 아이폰, 갤럭시, 아이패드...결정되지 않았으니까
    @Override
    public void actionPerformed(ActionEvent e) {
        //입력 수정 삭제 조회버튼이 클릭되면 이벤트를 JVM이 감지하고
        //감지되면 JVM이 actionPerformed메소드를 알아서 호출해줌
        Object obj= e.getSource();
        BookController bookController= null;
        BoardController boardController=null;
        //게시판
        if(obj==jbtn_board){
            gubun="boardMgr";
            if("boardMgr".equals(gubun)){
                System.out.println("게시판 선택");
                boardController = (BoardController)FrontController.getController(gubun);
                System.out.println("게시판 선택 =>" +boardController);
                gubun="bookMgr";//게시판 버튼 눌렀을때 보드mgr되었다. 다시 바꿔준다
            }
        }
        //너 도서 크루드 처리 할거니?
        else if("bookMgr".equals(gubun)){
            System.out.println("도서관리 선택");
            bookController = (BookController)FrontController.getController(gubun);
            System.out.println("도서관리 선택 =>"+bookController);
            //if문은 무조건 조건을 따진다.
            //if else문은 앞에 조건을 수렴하면 뒤에있는 코드는 설명기회를 갖지 않는다.

            //너 조회를 원해?--read
            if(obj==jbtn_sel) {
                System.out.println("조회");
            }
            //입력하려구?--create
            else if(obj==jbtn_ins){
                System.out.println("입력");
            }
            //수정할거야?--update
            else if(obj==jbtn_upd) {
                System.out.println("수정");
            }
            //탈퇴할거니?--delete
            else if(obj==jbtn_del) {
                System.out.println("삭제");
            }
        }
        //아님 게시판 크루드처리할까?
        else if("bMgr".equals(gubun)){

        }


    }//end of override
}//end of class
