package dev_java2.week7;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KiwiPanel3 extends JPanel implements ActionListener {
    String header[]={"부서번호","부서명","지역"};
//    String datas[][]=new String[0][3];
    String datas[][]={
        {"10","개발부", "서울"}
        ,{"20","인사부"," 부산"}
        ,{"30","부찌부","뉴욕"}
    };

    DefaultTableModel dtm = new DefaultTableModel(datas,header);//생성자 호출
    JTable jtb=new JTable(dtm);
    JScrollPane jsp=new JScrollPane(jtb, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    JTableHeader jth=jtb.getTableHeader();//헤더 크기 변경하기
    KiwiApp kiwiApp=null;
    public KiwiPanel3(){
        initDisplay();
    }
    public KiwiPanel3(KiwiApp kiwiApp){
        this();
        this.kiwiApp=kiwiApp;
    }
    public void ChangeFontSize(){
        jth.setFont(KiwiPanel1.f);
        jtb.setFont(KiwiPanel1.f);
    }
    public void initDisplay(){
        if(KiwiPanel1.isSize){
            ChangeFontSize();
        }
        //JPanel은 디폴트 레이아웃이 FlowLayout
        //JDialog, JFrame은 디폴트 레이아웃이 BorderLayout
        this.setLayout(new BorderLayout());
        this.add("Center",jsp);

        }

    @Override
    public void actionPerformed(ActionEvent e) {
    }



}
