package dev_java2.week7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class KiwiApp extends JFrame implements ActionListener {
    String imgPath="D:\\vscode_java\\dev_java\\images\\";
    Image img[]=null;
    ImageIcon imgs[] = new ImageIcon[3];
    String imgNames[] = {"admin2.png", "delay.png", "broken2.png"};

    JPanel jp_south=new JPanel();
    JPanel jp_center=new JPanel();
    JButton jbtn1 = new JButton();
    JButton jbtn2 = new JButton();
    JButton jbtn3 = new JButton();
    JButton imgButton[] = {jbtn1,jbtn2,jbtn3};
    Container conn= this.getContentPane();
    //컨테이너클래스는 JFrame에서만 주입 받을 수 있음
    //JPanel에서는 생성 불가함
    //생성자 파라미터를 통해서 넘겨서 사용함

    KiwiPanel1  kp1=null;
    KiwiPanel2 kp2=null;
    KiwiPanel3 kp3=null;
  public KiwiApp() {}
    public void initDisplay() {
        jbtn1.addActionListener(this);
        jbtn2.addActionListener(this);
        jbtn3.addActionListener(this);
        jp_center.setBackground(Color.CYAN);
        jp_south.setLayout(new GridLayout(1, 3));
        for(int i=0; i<imgs.length;i++){
            imgs[i]=new ImageIcon(imgPath+imgNames[i]);
            imgButton[i].setIcon(imgs[i]);
            imgButton[i].setBorderPainted(false);
            imgButton[i].setFocusPainted(false);
            imgButton[i].setContentAreaFilled(false);
            jp_south.add(imgButton[i]);
        }
        jp_south.add(jbtn1);
        jp_south.add(jbtn2);
        jp_south.add(jbtn3);
        this.add("South", jp_south);
        this.add("Center", jp_center);
        this.setSize(400, 500);
        this.setVisible(true);
    }
    public static void main(String[] args) {
        KiwiApp kApp = new KiwiApp();
        kApp.initDisplay();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        //너 첫번째 버튼 누른거야?
        if(obj == jbtn1) {
            System.out.println("첫번째");
            if(kp2!=null){
//                conn.remove(kp2.jta);
//                conn.remove(kp2.jtf);
                conn.remove(kp2);
            }
            if(kp3!=null){
                conn.remove(kp3.jsp);
                conn.remove(kp3);
            }
            kp1=new KiwiPanel1();
            this.add("Center",kp1);
            conn.revalidate();//화면 재조정
        }else if(obj == jbtn2) {//두번째 버튼 누른거야?
            System.out.println("두번째");
            if(kp1!=null){
//                conn.remove(kp1.jbtn1);
//                conn.remove(kp1.jbtn2);
//                conn.remove(kp1.jbtn3);
                conn.remove(kp1);
            }
            if(kp3!=null){
//                conn.remove(kp3.jsp);
                conn.remove(kp3);
            }
            kp2=new KiwiPanel2();
            this.add("Center",kp2);
            conn.revalidate();//화면 재조정
        }else if (obj == jbtn3) {//세번째 버튼 원해?

            System.out.println("세번째");
            if(kp1!=null){
                conn.remove(kp1.jbtn1);
                conn.remove(kp1.jbtn2);
                conn.remove(kp1.jbtn3);
                conn.remove(kp1);
            }
            if(kp2!=null){
                conn.remove(kp2.jta);
                conn.remove(kp2.jtf);
                conn.remove(kp2);
            }
            kp3=new KiwiPanel3();
            this.add("Center",kp3);
            conn.revalidate();//화면 재조정
        }
    }
}
