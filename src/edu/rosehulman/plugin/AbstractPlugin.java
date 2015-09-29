package edu.rosehulman.plugin;

import edu.rosehulman.gui.IExecutionModule;

public abstract class AbstractPlugin {
	private boolean started = false;
	private boolean paused = false;
	private IExecutionModule executionModule;

	public AbstractPlugin(IExecutionModule executionModule) {
		this.executionModule = executionModule;
	}

	public final void onStart() {
		System.out.println(this.getClass().getSimpleName());

		if (!started) {
			started = true;
			paused = false;
			start();
		} else {
			throw new IllegalStateException("Plugin is already started");
		}
	}

	public final void onStop() {
		if (started && !paused) {
			started = false;
			paused = true;
			stop();
		} else {
			throw new IllegalStateException("Cannot stop a paused or stopped plugin");
		}
	}

	public final void onPause() {
		if (started && !paused) {
			paused = true;
			pause();
		} else {
			throw new IllegalStateException("Cannot pause a paused or stopped plugin");
		}
	}

	public final void onResume() {
		if (started && paused) {
			paused = false;
			resume();
		} else {
			throw new IllegalStateException("Cannot resume a running or stopped plugin");
		}
	}

	public final IExecutionModule getExecutionModule() {
		return this.executionModule;
	}
	
	public final boolean isStarted() {
		return started;
	}
	
	public final boolean isPaused() {
		return paused;
	}

	protected abstract void start();

	protected abstract void stop();

	protected abstract void pause();

	protected abstract void resume();
}
