package com.xinlab.blueapple.test;

import java.awt.Color;

public class Enemy implements Runnable {

    int x, y;

    private Color color;// 小球颜色

    private int R = 34;// 小球半径

    private int xSpeed;// 小球x轴速度

    private int ySpeed;// 小球y轴速度

    int a, b, c;

    public void run() {
        // TODO Auto-generated method stub
        try {
            Thread.sleep(3);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        x = getX() + xSpeed;
        y = getY() + ySpeed;
        if ((getX() + R) > 690 || getX() < 0) {
            xSpeed = -xSpeed;
        }
        if (getY() < 0 || (getY() + R) > 580) {
            ySpeed = -ySpeed;
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getR() {
        return R;
    }

    public void setR(int r) {
        R = r;
    }

    public int getxSpeed() {
        return xSpeed;
    }

    public void setxSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public int getySpeed() {
        return ySpeed;
    }

    public void setySpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public Enemy() {
        // rgb随机颜色的设置
        a = (int) (Math.random() * 255);
        b = (int) (Math.random() * 255);
        c = (int) (Math.random() * 255);

        // 随机x y速度
        xSpeed = (int) (Math.random() * 8 + 3);
        ySpeed = (int) (Math.random() * 8 + 3);
        color = new Color(a, b, c);
        // 随机坐标
        x = (int) (Math.random() * 600 + 1);
        y = (int) (Math.random() * 500 + 1);
    }

}