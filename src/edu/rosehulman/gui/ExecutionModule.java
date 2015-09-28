package edu.rosehulman.gui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class ExecutionModule extends JPanel implements IExecutionModule {
	
	public ExecutionModule() {
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder("Active Plugin"));
	}

	@Override
	public void showComponent(JComponent component) {
		this.clear();
		this.add(component, BorderLayout.CENTER);
	}

	@Override
	public void clear() {
		this.removeAll();
		this.repaint();
		this.revalidate();
	}
	
	@Override
	public int getWidth() {
		return super.getWidth();
	}
	
	@Override
	public int getHeight() {
		return super.getHeight();
	}
}
