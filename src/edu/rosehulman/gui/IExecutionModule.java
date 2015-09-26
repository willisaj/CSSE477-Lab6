package edu.rosehulman.gui;

import javax.swing.JComponent;

public interface IExecutionModule {
	
	public void showComponent(JComponent component);
	
	public void clear();
	
	public int getHeight();
	
	public int getWidth();

}
