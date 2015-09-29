package edu.rosehulman.plugin;

import javax.swing.JPanel;

import edu.rosehulman.gui.IExecutionModule;

public abstract class AbstractPlugin {
	protected boolean started = false;
	protected boolean paused = false;
	protected IExecutionModule executionModule;

	public AbstractPlugin(IExecutionModule executionModule) {
		this.executionModule = executionModule;
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
}
