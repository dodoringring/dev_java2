package dev_java2.week7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KiwiPanel2 extends JPanel implements ActionListener {
    KiwiApp kiwiApp=null;
    JTextArea jta=new JTextArea("");
    JTextField jtf=new JTextField("");
    //이른 인스턴스화로 할까? 아님 게으른이로 할까?
    //생성자는 필요한가?
    //생성자가 필요하다고 생각했다면 KiwiPanel1
    //필요하다고 생각했다면 어떤 타입이 왜 와야하는지 말할 수있다.
    public KiwiPanel2(){
        initDisplay();
    }
    public KiwiPanel2(KiwiApp kiwiApp){
        this();//15번 자기자신 생성자 호출하기.
        this.kiwiApp=kiwiApp;
    }
    public void ChangeFontSize(){
        jta.setFont(KiwiPanel1.f);
        jtf.setFont(KiwiPanel1.f);
    }
    public void initDisplay(){
        if(KiwiPanel1.isSize){
            ChangeFontSize();
        }
      this.setLayout(new BorderLayout());
      this.add("Center",jta);
      this.add("South",jtf);
      this.setSize(400,400);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
