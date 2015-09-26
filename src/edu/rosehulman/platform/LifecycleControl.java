package edu.rosehulman.platform;

import edu.rosehulman.plugin.Plugin;

public class LifecycleControl {
	public void startPlugin(Plugin plugin) {
		// TODO: system-level startup
		
		
		plugin.onStart();
	}

	public void stopPlugin(Plugin plugin) {
		// TODO: system-level stop
		
		
		plugin.onStop();
	}

	public void pausePlugin(Plugin plugin) {
		// TODO: system-level pause
		
		
		plugin.onPause();

	}

	public void resumePlugin(Plugin plugin) {
		// TODO: system-level resume
		
		
		plugin.onResume();
	}
}
