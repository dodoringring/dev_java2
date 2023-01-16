package dev_java2.bookMVC;

public class FrontController {
    //return타입을 Object로 준 이유는 무엇인가?
    //BookController가 올수도있고 BoardController가 올수도 있기 때문에
    public static Object getController(String gubun){
        Object obj=null;//BookController or BoardController
        if("bookMgr".equals(gubun)){
            obj=new BookController();
        } else if ("boardMgr".equals(gubun)) {
            obj=new BoardController();
        }
        return obj;
    }
}
