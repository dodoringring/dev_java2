package dev_java2.bookMVC;

import java.util.List;
import java.util.Map;

//데이터 액세스 오브젝트
public class BookDao {
    /**
     * 도서목록
     * select bk_no, bk_title from book
     * where bk_title(?)=?
     * where bk_author(?))=?
     * @param cols 컬럼명 bk_title or bk_aurthor or bk_info
     * @param keyword 텍스트필드에 사용자가 입력한 값
     * @return 검색결과는 n개 로우 List<Map<>>
     * 조인이필수적인 경우에는 반드시 List<MAp>형태가 유리하고
     * 그렇지 않으면 List<XXXVO>형태가 별 차이없다.
     * 단, 조회결과로 얻은 정보를 자바코등 연산을 해야하는 경우라면
     * 제네릴 타입으로 Map보다는 XXXVO가 유리함
     * Map이면 리턴값이 무조건 Object이다. ClassCastingException
     * int i = Integer,parseInt(pMap.get("bk_no").toString())
     * int i = XXXVO.getBkno();--->맵을 쓰면 훨씬 더 간단하다.
     */
    public List<Map<String, Object>> getBookList(String cols, String keyword){
        System.out.println("도서목록조회");
        List<Map<String, Object>> bList =null;
        return bList;
    }

    /**********************************************
     * 도서정보 삭제구현
     * @param bk_no - 도서번호
     * @return 1이면 삭제성공, 0이면 삭제실패
     * *********************************************
     */
    public int bookDelete(int bk_no) {
        System.out.println("bookDelete호출 ==>"+bk_no);
        return 0;
    }

    /**
     *도서정보 입력하기
     * @param bkVO-수정요청으로 입력받은값
     * @return 1이면 수정 성공, 0이면 수정실패
     */
    public int bookUpdate(BookVO bkVO) {
        System.out.println("bookUpdate호출(사용자가 선택한 도서정보-주서번지 출력됨) ==>"+bkVO);
        int result=0;
        //롬복 어노테이션 @Data를 사용했기에 getter/setter메소드는 없지만 사용가능함
        //단, VO타입이므로 전변에 담긴 값을 출력하려면 getter메소드 호출함
        //private는 캡슐화로 인해 직접 접근 불가능하고 위변조로 인한 피해로부터 보호한다.
        //접근제한자는 반드시 private으로 할것. 전역변수 사용하지 않음
        result=1;        return result;
    }

    /**
     *도서정보 입력하기
     * @param bkVO-수정요청으로 입력받은값
     * @return 1이면 수정 성공, 0이면 수정실패
     */
    public int bookInsert(BookVO bkVO) {
        int result=0;
        System.out.println("bookInsert호출 ==>"+bkVO);
        return result;
    }
}
