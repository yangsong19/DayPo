package com.xinlab.blueapple.contenttool.test;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.max;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;

public class CircleLayout implements LayoutManager {

	int maxCompWidth = 0; // ���������
	int maxCompHeight = 0; // �������߶�

	int lc = 0; // �ߵĸ������αߵĸ���

	double v = 1.5D; // ��������
	double cd = 0; // ��������֮��ľ���(���ĵ㵽���ĵ�)

	int l; // ������α߳�/2

	int lw = 0; // �����ı߳�
	int lh = 0;

	public CircleLayout() {
		this(1.5D);
	}

	public CircleLayout(double factor) {
		this.v = factor;
	}

	public void addLayoutComponent(String name, Component comp) {

	}

	public void layoutContainer(Container parent) {

		synchronized (parent.getTreeLock()) {

			int cs = parent.getComponentCount();

			if (cs == 1) {
				layoutContainer1(parent);
				return;
			}

			if (cs == 2) {
				layoutContainer2(parent);
				return;
			}

			Dimension d = parent.getPreferredSize();

			Point cp = new Point(d.width / 2, d.height / 2);

			double dx = 0, dy = 0;
			int cpx = 0, cpy = 0;
			for (int i = 0; i < cs; i++) {
				dx = sin(i * (2 * PI / lc)) * l;
				dy = -cos(i * (2 * PI / lc)) * l;

				cpx = (int) (cp.x + dx);
				cpy = (int) (cp.y + dy);
				Point cpp = new Point(cpx, cpy);
				Component comp = parent.getComponent(i);
				Dimension compSize = comp.getPreferredSize();

				comp.setBounds(calcR(cpp, compSize));
			}
		}
	}

	public Dimension minimumLayoutSize(Container parent) {
		return new Dimension(lw, lh);
	}

	public Dimension preferredLayoutSize(Container parent) {

		lc = parent.getComponentCount();

		Component[] components = parent.getComponents();
		for (Component component : components) {
			Dimension d = component.getPreferredSize();
			this.maxCompWidth = max(d.width, this.maxCompWidth);
			this.maxCompHeight = max(d.height, this.maxCompHeight);
		}

		calc();

		return new Dimension(lw, lh);

	}

	public void removeLayoutComponent(Component comp) {

	}

	// -------------------------------

	private void layoutContainer1(Container parent) {
		Component c = parent.getComponent(0);
		c.setBounds(0, 0, lw, lh);
	}

	private void layoutContainer2(Container parent) {

		Component c = parent.getComponent(0);
		Dimension d = c.getPreferredSize();
		c.setBounds(0, 0, d.width, d.height);

		c = parent.getComponent(1);
		d = c.getPreferredSize();

		c.setBounds(lw - d.width, lh - d.height, d.width, d.height);

	}

	private void calc() {

		if (lc == 1) {
			lw = maxCompWidth;
			lh = maxCompHeight;
			return;
		}

		cd = max(maxCompWidth, maxCompHeight) * v;

		if (lc == 2) {
			double yy = sqrt(cd * cd * 2) / 2;
			l = (int) yy + 1;
			lh = l + maxCompHeight;
			lw = l + maxCompWidth;
			return;
		}

		// ��������ε�һ���߳��������������������Բ�ε����о��εı߳�������ֵ��
		// ʹ�����Ҷ��?
		double x = cd / sin(2 * PI / lc);
		l = (int) x + 1;
		lh = 2 * l + maxCompHeight;
		lw = 2 * l + maxCompWidth;
	}

	private Rectangle calcR(Point p, Dimension d) {
		int y = p.y - d.height / 2;
		int x = p.x - d.width / 2;
		return new Rectangle(new Point(x, y), d);
	}
}
