package edu.rosehulman.platform;

import edu.rosehulman.plugin.AbstractPlugin;

public class LifecycleControl {
	public void startPlugin(AbstractPlugin plugin) {
		// TODO: system-level startup
		
		
		plugin.onStart();
	}

	public void stopPlugin(AbstractPlugin plugin) {
		// TODO: system-level stop
		
		
		plugin.onStop();
	}

	public void pausePlugin(AbstractPlugin plugin) {
		// TODO: system-level pause
		
		
		plugin.onPause();

	}

	public void resumePlugin(AbstractPlugin plugin) {
		// TODO: system-level resume
		
		
		plugin.onResume();
	}
}
