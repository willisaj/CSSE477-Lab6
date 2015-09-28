package edu.rosehulman.platform;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import edu.rosehulman.gui.ExecutionModule;
import edu.rosehulman.gui.ListingModule;
import edu.rosehulman.gui.StatusModule;

public class PluginApp extends JFrame {
	
	private IExecutionModule executionModule;
	private StatusModule statusModule;
	private PluginStatusModule pluginStatusModule;
	
	public PluginApp() {
		super("Super Awesome Application for Running Plugins");
		// TODO TODO: set stdout to route to the status module
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setSize(1440, 810);

		//ExecutionModule
		this.executionModule = new ExecutionModule();
		this.add((JComponent)executionModule, BorderLayout.CENTER);
		
		//Listing Module
		ListingModule listing = new ListingModule(PluginManager.PLUGIN_ROOT);
		this.add(listing, BorderLayout.WEST);
		
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());
		
		//Status Module
		this.statusModule = new StatusModule(1220, 610);
		southPanel.add(statusModule, BorderLayout.CENTER);
		
		//Plugin Status Module
		this.pluginStatusModule = new PluginStatusModule();
		this.pluginStatusModule.setPreferredSize(new Dimension(200, 200));
		this.pluginStatusModule.showNoPlugin();
		southPanel.add(this.pluginStatusModule, BorderLayout.WEST);
		
		this.add(southPanel, BorderLayout.SOUTH);
	}
	
	public static void main(String[] args) {
		PluginApp app = new PluginApp();
		app.setVisible(true);
	}

}
