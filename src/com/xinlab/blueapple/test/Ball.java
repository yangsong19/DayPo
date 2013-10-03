package com.xinlab.blueapple.test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Ball extends JFrame {
    MyPanel mp;

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Container se = new Container();
    }

    public Ball(int n) {

        mp = new MyPanel(n);
        mp.addMouseListener(mp);
        mp.addMouseMotionListener(mp);
        this.add(mp);
        this.setTitle("是男人就坚持100s");
        this.setLocation(200, 100);
        this.setSize(700, 610);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

}

class MyPanel extends JPanel implements MouseListener, MouseMotionListener {
    boolean touch = false;

    int x = 100, y = 100;

    String string = "坚持时间：0.0";

    double time = 0.0;

    int n;

    /**
     * 创建一个自己的画板
     */
    // 创建敌方小球
    Vector<Enemy> ee = new Vector<Enemy>();

    public MyPanel(int m) {
        n = m;
        for (int i = 0; i < n; i++) {
            ee.add(new Enemy());
        }
    }

    public void paint(Graphics g) {
        for (int i = 0; i < ee.size(); i++) {
            Enemy et1 = ee.get(i);
            if (((x - 15) - (et1.getX() - et1.getR() / 2))
                    * ((x - 15) - (et1.getX() - et1.getR() / 2))
                    + ((y - 15) - (et1.getY() - et1.getR() / 2))
                    * ((y - 15) - (et1.getY() - et1.getR() / 2)) <= (et1.getR() / 2 + 15)
                    * (et1.getR() / 2 + 15)) {
                touch = true;
            }

        }
        super.paint(g);
        super.setBackground(Color.BLACK);

        for (int i = 0; i < ee.size(); i++) {
            Enemy etEnemy = ee.get(i);
            etEnemy.run();
            g.setColor(etEnemy.getColor());
            g.fillOval(etEnemy.getX(), etEnemy.getY(), etEnemy.getR(), etEnemy
                    .getR());
        }
        g.setColor(Color.yellow);
        g.fillRect(x, y, 30, 30);
        g.drawString(string, 150, 10);
        if (!touch) {
            repaint();
            time += 0.004;
            string = "坚持时间：" + time;
        } else {
            g.setFont(new Font("宋体", Font.BOLD, 20));
            g.drawString("游戏结束，坚持时间：" + time, 150, 150);
            time = 0;
            string = "坚持时间：0.0";

        }

    }

    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        touch = false;
        repaint();

    }

    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stu
        x = e.getX();
        y = e.getY();
        if (x >= 660 || x <= 1 || y >= 550 || y <= 1) {
            touch = true;
        }
        for (int i = 0; i < ee.size(); i++) {
            Enemy et1 = ee.get(i);
            if (((x - 10) - (et1.getX() - et1.getR() / 2))
                    * ((x - 10) - (et1.getX() - et1.getR() / 2))
                    + ((y - 10) - (et1.getY() - et1.getR() / 2))
                    * ((y - 10) - (et1.getY() - et1.getR() / 2)) < (et1.getR() / 2 + 10)
                    * (et1.getR() / 2 + 10)) {

                touch = true;
            }

        }
    }

    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }

}

class Container extends JFrame {
    int count;

    JPanel jp, jp1;

    JLabel jl;

    JTextField jtf;

    JButton jb;

    Container() {
        jp = new JPanel();
        jp1 = new JPanel();
        jl = new JLabel("小球个数：");
        jtf = new JTextField(10);
        jb = new JButton("确定");
        jb.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                count = (int) Integer.valueOf(jtf.getText());
                setVisible(false);
                Ball b = new Ball(count);
            }
        });

        jp.add(jl);
        jp.add(jtf);
        jp1.add(jb);

        this.add(jp);
        this.add(jp1);

        this.setTitle("设定小球个数");
        this.setLayout(new GridLayout(2, 1));
        this.setLocation(400, 200);
        this.setSize(153, 145);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setVisible(true);
    }
}