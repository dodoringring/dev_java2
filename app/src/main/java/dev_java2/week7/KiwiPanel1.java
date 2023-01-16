package dev_java2.week7;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class KiwiPanel1 extends JPanel implements ActionListener {
    //이른 인스턴스화로 할까? 아님 게으른인스턴스화로 할까?
    //생성자는 필요한가?
    //생성자가 필요하다고 생각했다면 KiwiPanel1
    //필요하다고 생각했다면 어떤 타입이 왜 와야하는지 말할 수있다.


    KiwiApp kApp = null;
    JButton jbtn1= new JButton("소연");
    JButton jbtn2= new JButton("도희");
    JButton jbtn3= new JButton("은재");
    //반드시 static 으로 해야하는 이유는 패널 1번에서 결정되 설정이 패널 2에도, 또 패널 3에도 적용되어야 하니까.
    static Font f =null;
    static boolean isSize = false;
    public KiwiPanel1(){
        initDisplay();
    }
    public void initDisplay(){
        this.setLayout(null);
        jbtn1.addActionListener(this);
        jbtn2.addActionListener(this);
        jbtn3.addActionListener(this);
        jbtn1.setBounds(140, 80, 100, 30);
        jbtn2.setBounds(140, 150, 100, 30);
        jbtn3.setBounds(140, 220, 100, 30);
        this.add(jbtn1);
        this.add(jbtn2);
        this.add(jbtn3);
    }

    public static void main(String[] args) {
        KiwiPanel1 kp1 = new KiwiPanel1();
        kp1.initDisplay();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    if(e.getSource() == jbtn3){
        if(!isSize){
            f=new Font("굴림체", Font.BOLD,16);
            jbtn1.setFont(f);
            jbtn2.setFont(f);
            jbtn3.setFont(f);
            isSize = true;
        }else{
            f=new Font("굴림체", Font.PLAIN,16);
            jbtn1.setFont(f);
            jbtn2.setFont(f);
            jbtn3.setFont(f);
            isSize = false;
        }
    }
    }
    /*
    생성자 안에서 initDisplay()를 호출하는것과 그렇지 않은것의 차이는 뭘까?
    위치의 문제이면 화면 정의서의 요구사항에 따라서 다른 선택이 되어야한다.

     */

}
