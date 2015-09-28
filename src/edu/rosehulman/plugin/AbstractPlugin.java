package edu.rosehulman.plugin;

import javax.swing.JPanel;

import edu.rosehulman.gui.IExecutionModule;

public abstract class AbstractPlugin {
	protected IExecutionModule panel;
	protected boolean started = false;
	protected boolean paused = false;

	public AbstractPlugin(IExecutionModule executionModule) {
		this.panel = executionModule;
	}

	public void onStart() {
		// TODO: default start
		if (!started) {
			started = true;
			paused = false;
		} else {
			throw new IllegalStateException("Plugin is already started");
		}
	}

	public void onStop() {
		// TODO: default stop
		if (started && !paused) {
			started = false;
			paused = true;
		} else {
			throw new IllegalStateException("Cannot stop a paused or stopped plugin");
		}
	}

	public void onPause() {
		// TODO: default pause
		if (started && !paused) {
			paused = true;
		} else {
			throw new IllegalStateException("Cannot pause a paused or stopped plugin");
		}
	}

	public void onResume() {
		// TODO: default resume
		if (started && paused) {
			paused = false;
		} else {
			throw new IllegalStateException("Cannot resume a running or stopped plugin");
		}
	}
	
	protected IExecutionModule getExecutionModule() {
		return this.panel;
	}
}
