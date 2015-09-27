package edu.rosehulman.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PluginStatusModule extends JPanel {
	
	private static String NO_PLUGIN_STRING = "No plugin selected.";
	private static String INACTIVE_PLUGIN_STRING = "Press the button below to start the plugin.";
	private static String ACTIVE_PLUGIN_STRING = "Stop or pause the plugins using the buttons below.";
	private static String PAUSED_PLUGIN_STRING = "Press the button below to resume the plugin.";
	
	public PluginStatusModule() {
		this.setLayout(new BorderLayout());
	}
	
	public void showNoPlugin() {
		this.removeAll();
		this.add(new JLabel(NO_PLUGIN_STRING), BorderLayout.CENTER);
		this.refresh();
	}
	
	public void showInactivePlugin(String pluginInfo, ActionListener playListener) {
		this.removeAll();
		this.add(new JLabel(pluginInfo), BorderLayout.NORTH);
		this.add(new JLabel(INACTIVE_PLUGIN_STRING), BorderLayout.CENTER);
		
		JButton playButton = new JButton("Start Plugin");
		playButton.addActionListener(playListener);
		this.add(playButton, BorderLayout.SOUTH);
		
		this.refresh();
	}
	
	public void showActivePlugin(String pluginInfo, ActionListener stopListener, ActionListener pauseListener) {
		this.removeAll();
		this.add(new JLabel(pluginInfo), BorderLayout.NORTH);
		this.add(new JLabel(ACTIVE_PLUGIN_STRING), BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		
		JButton stopButton = new JButton("Stop Plugin");
		stopButton.addActionListener(stopListener);
		buttonPanel.add(stopButton, BorderLayout.WEST);
		JButton pauseButton  = new JButton("Pause Plugin");
		pauseButton.addActionListener(pauseListener);
		buttonPanel.add(pauseButton, BorderLayout.EAST);
		
		this.refresh();
	}
	
	public void showPausedPlugin(String pluginInfo, ActionListener resumeListener) {
		this.removeAll();
		this.add(new JLabel(pluginInfo), BorderLayout.NORTH);
		this.add(new JLabel(PAUSED_PLUGIN_STRING), BorderLayout.CENTER);
		
		JButton resumeButton = new JButton("Resume Plugin");
		resumeButton.addActionListener(resumeListener);
		this.add(resumeButton, BorderLayout.SOUTH);
		
		this.refresh();
	}
	
	private void refresh() {
		this.revalidate();
		this.repaint();
	}

}
