package edu.rosehulman.gui;

import javax.swing.JTextArea;

public interface IStatusModule {
	
	public void addStatusMessage(String message);
	
	public void clearStatusMessages();
	
	public JTextArea getStatusTextArea();

}
