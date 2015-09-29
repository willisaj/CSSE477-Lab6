package edu.rosehulman.platform;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import edu.rosehulman.gui.ExecutionModule;
import edu.rosehulman.gui.IExecutionModule;
import edu.rosehulman.gui.IStatusModule;
import edu.rosehulman.gui.ListingModule;
import edu.rosehulman.gui.PluginStatusModule;
import edu.rosehulman.gui.StatusModule;

public class PluginApp extends JFrame {
	private IExecutionModule executionModule;
	private IStatusModule statusModule;
	private PluginStatusModule pluginStatusModule;
	private LifecycleController lifecycleController;

	public PluginApp() throws IOException {
		super("Super Awesome Application for Running Plugins");

		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setSize(1440, 810);

		// ExecutionModule
		this.executionModule = new ExecutionModule();
		this.add((JComponent) executionModule, BorderLayout.CENTER);

		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());

		// Status Module
		this.statusModule = new StatusModule(1220, 610);
		southPanel.add((JComponent) statusModule, BorderLayout.CENTER);

		// Plugin Status Module
		this.pluginStatusModule = new PluginStatusModule();
		this.pluginStatusModule.setPreferredSize(new Dimension(200, 200));
		this.pluginStatusModule.showNoPlugin();
		southPanel.add(this.pluginStatusModule, BorderLayout.WEST);

		// LifecycleController
		this.lifecycleController = new LifecycleController(executionModule, pluginStatusModule);
		
		// Listing Module
		ListingModule listing = new ListingModule("file:///" + LifecycleController.PLUGIN_ROOT, lifecycleController);
		listing.setSize(200, 610);
		listing.setPreferredSize(new Dimension(200, 610));
		this.add(listing, BorderLayout.WEST);

		this.add(southPanel, BorderLayout.SOUTH);
	}
	
	public IStatusModule getStatusModule() {
		return this.statusModule;
	}

	public static void main(String[] args) throws IOException {
		PluginApp app = new PluginApp();
		
		StatusOutputStream statusOutputStream = new StatusOutputStream(app.getStatusModule().getStatusTextArea());
		PrintStream printStream = new PrintStream(statusOutputStream);
		System.setOut(printStream);
//		System.setErr(printStream);
		
		app.setVisible(true);
	}
}
