package com.xinlab.blueapple.contenttool.test;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

import javax.swing.JPopupMenu;

public class CirclePopupMenu extends JPopupMenu {

	private static final long serialVersionUID = 7318307166817760768L;

	double factor;

	public CirclePopupMenu() {
		this(1.5D);
	}

	public CirclePopupMenu(double factor) {
		this.factor = factor;
		this.setLayout(new CircleLayout(factor));
		setBorderPainted(false);

	}

	@Override
	public boolean contains(int x, int y) {

		int compSize = getComponentCount();

		Point menuPoint = getLocationOnScreen();

		Component c = null;
		Point cp = null;
		Dimension d = null;

		for (int i = 0; i < compSize; i++) {
			c = getComponent(i);

			cp = c.getLocationOnScreen();
			int dx = cp.x - menuPoint.x;
			int dy = cp.y - menuPoint.y;
			d = c.getSize();
			Rectangle r = new Rectangle(dx, dy, d.width, d.height);

			if (r.contains(x, y)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void show(Component invoker, int x, int y) {

		setInvoker(invoker);

		Point invokerOrigin;
		if (invoker != null) {
			invokerOrigin = invoker.getLocationOnScreen();

			long lx, ly;
			lx = ((long) invokerOrigin.x) + ((long) x);
			ly = ((long) invokerOrigin.y) + ((long) y);

			// ------
			Dimension myDim = this.getPreferredSize();

			lx = lx - myDim.width / 2;
			ly = ly - myDim.height / 2;
			// ------

			if (lx > Integer.MAX_VALUE)
				lx = Integer.MAX_VALUE;
			if (lx < Integer.MIN_VALUE)
				lx = Integer.MIN_VALUE;
			if (ly > Integer.MAX_VALUE)
				ly = Integer.MAX_VALUE;
			if (ly < Integer.MIN_VALUE)
				ly = Integer.MIN_VALUE;

			setLocation((int) lx, (int) ly);
		} else {
			setLocation(x, y);
		}
		setVisible(true);

	}

	@Override
	public void paintComponent(Graphics g) {
		screenCapture();
		if (isRobotSupport) {
			g.drawImage(background, 0, 0, null);
		} else {
			super.paintComponent(g);
		}
	}

	private BufferedImage background = null;
	private boolean isRobotSupport = true;

	private void screenCapture() {
		if (isRobotSupport) {
			Point pos = getLocationOnScreen();
			try {
				Robot rbt = new Robot();
				background = rbt.createScreenCapture(new Rectangle(pos,
						getSize()));
			} catch (Exception ex) {
				background = null;
				isRobotSupport = false;
			}
		}
	}

}
