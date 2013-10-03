package com.xinlab.blueapple.contenttool.test;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;

public class TestW extends JFrame {

	private static final long serialVersionUID = -5857751671422862570L;

	JPopupMenu pm = new CirclePopupMenu();

	public static void main(String[] args) {

		TestW t = new TestW();
		t.setBackground(Color.GREEN);
		t.setSize(640, 480);
		t.pack();
		t.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		t.setVisible(true);
	}

	public TestW() {

		pm.add(new ColorMenuItem("A"));
		pm.add(new ColorMenuItem("B"));
		pm.add(new ColorMenuItem("C"));
		pm.add(new ColorMenuItem("D"));
		pm.add(new ColorMenuItem("E"));
		pm.add(new ColorMenuItem("F"));

		pm.add(new ColorMenuItem("A"));
		pm.add(new ColorMenuItem("B"));
		pm.add(new ColorMenuItem("C"));
		pm.add(new ColorMenuItem("D"));
		pm.add(new ColorMenuItem("E"));

		// pm.add(pm2);

		setLayout(new CircleLayout());

		add(new JButton("Button1"));
		add(new JButton("Button2"));
		// add(new JButton("Button3"));

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					pm.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
	}
}
