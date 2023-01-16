package dev_java2.bookMVC;

public interface Controller {
    //임플리먼츠 단독으로 인스턴스화 할 수 없다. 반드시 구현체클래스가 있어야한다.
    //추상클래스는 인터페이스와는 다르게 생성자를 가질 수 있다.
    //인터페이스는 생성자 가질 수 없다.
    //추상클래스는 추상메소드뿐만아니라 일반메소드도 가질 수 있다.
    //구현체클래스를 통해서 같은 메소드를 상속받아도 기능이 다를수있다.
    //인터페이스는 추상 메소드만 가질 수 있다. 일반 메소드는  불가능하다.
    //일반 전변 불가능하다.
    public abstract FrontController getController(String gubun);


}
