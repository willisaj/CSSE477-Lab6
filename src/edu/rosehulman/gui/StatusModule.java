package edu.rosehulman.gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class StatusModule extends JPanel {
	private JScrollPane scrollPane;
	private JTextArea statusMessages;
	
	public StatusModule(int height, int width) {
		this.setLayout(new BorderLayout());
		
		statusMessages = new JTextArea(10, 10);
		
		statusMessages.setEnabled(false);
		statusMessages.setDisabledTextColor(Color.BLACK);
		scrollPane = new JScrollPane(statusMessages);
		this.add(scrollPane, BorderLayout.CENTER);
	}
	
	public void addStatusMessage(String message) {
		statusMessages.setText(statusMessages.getText() + message + "\n");
	}
	
	public void clearStatusMessages() {
		statusMessages.setText("");
	}

}
