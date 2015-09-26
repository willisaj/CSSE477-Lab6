package edu.rosehulman.platform;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import edu.rosehulman.gui.StatusModule;

public class PluginApp extends JFrame {
	
	private StatusModule statusModule;
	
	public PluginApp() {
		super("Super Awesome Application for Running Plugins");
		
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setSize(1440, 810);

		Button execution = new Button("execution");
		this.add(execution, BorderLayout.CENTER);
		Button listing = new Button("Listing");
		this.add(listing, BorderLayout.WEST);
		
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());
		
		//Status Module
		this.statusModule = new StatusModule(1220, 610);
		southPanel.add(statusModule, BorderLayout.CENTER);
		
		Button controlModule = new Button("Control MOdule");
		controlModule.setPreferredSize(new Dimension(200, 200));
		controlModule.setSize(new Dimension(200, 200));		
		southPanel.add(controlModule, BorderLayout.WEST);
		this.add(southPanel, BorderLayout.SOUTH);
	}
	
	public static void main(String[] args) {
		PluginApp app = new PluginApp();
		app.setVisible(true);
	}

}
