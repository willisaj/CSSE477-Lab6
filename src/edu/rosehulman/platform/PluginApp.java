package edu.rosehulman.platform;

import java.awt.BorderLayout;
import java.awt.Button;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class PluginApp extends JFrame {
	
	public PluginApp() {
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		Button b = new Button("Listing");
		this.add(b, BorderLayout.EAST);
	}
	
	public static void main(String[] args) {
		PluginApp app = new PluginApp();
		app.show();
	}

}
