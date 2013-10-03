package com.xinlab.blueapple.contenttool.test;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class ColorMenuItem extends JMenuItem {

	private static final long serialVersionUID = -37279345652062832L;

	public ColorMenuItem(String title) {
		super(title);
		setBackground(Color.YELLOW);

		addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(getParent(), getText());
			}
		});
	}
}
