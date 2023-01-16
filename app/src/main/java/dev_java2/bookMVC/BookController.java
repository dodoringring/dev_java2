package dev_java2.bookMVC;

import java.util.List;
import java.util.Map;

public class BookController{
//public class BookController implements Controller{

//원래는 BookLogic 클래스를 객체주입해야하지만 업무적인 depth가
//얕아서 로직 클래스에서 선택, 결정에 따른 추가적인 프로세스가 전혀 없는 상태임.
//따라서 컨트롤러클래스와 Dao클래스 사이에 연결만 담당하니까 의미없음
    BookDao bookDao=new BookDao();
//    BookLogic bookLogic = new BookLogic();
    //조회
    public List<Map<String, Object>> getBookList(String cols, String keyword){
        System.out.println("도서목록조회");
        List<Map<String, Object>> bList =null;
        bList=bookDao.getBookList(cols, keyword);
        return bList;
    }
    //입력
    public int bookInsert(BookVO bkVO){
        System.out.println("도서정보 등록");
        int result = 0;
        result=bookDao.bookInsert(bkVO);
        return result;
    }

    //수정
    public int bookUpdate(BookVO bkVO){
        System.out.println("도서정보 수정");
        int result = 0;
        result=bookDao.bookUpdate(bkVO);
        return result;
    }

    //삭제
    //쿼리문을 보고 리턴타입과 파라미터 타입 및 갯수 결정하는데 참고
    //DELETE FROM book WHERE bk_no=5;
    //리턴타입 int -1 :삭제성공 , 0: 삭제실패
    //파라미터타입 int-도서번호
    public int bookDelete(int bk_no){
        System.out.println("도서정보 삭제");
        int result = 0;
        result=bookDao.bookDelete(bk_no);
        return result;
    }

/*
  SQL응용
  JAVA와 오라클 연동하기
  JDBC API(원시적인)-MyBatis(반자동):중심-Hibernate :독이다!!
 */
}