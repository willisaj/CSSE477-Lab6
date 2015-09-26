package edu.rosehulman.gui;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class ExecutionModule extends JPanel implements IExecutionModule {
	
	public ExecutionModule() {
		this.setLayout(new BorderLayout());
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

}
