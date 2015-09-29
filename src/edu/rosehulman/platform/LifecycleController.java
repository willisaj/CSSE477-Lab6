package edu.rosehulman.platform;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import edu.rosehulman.gui.IExecutionModule;
import edu.rosehulman.gui.ListingModuleListener;
import edu.rosehulman.gui.PluginStatusModule;
import edu.rosehulman.plugin.AbstractPlugin;

public class LifecycleController implements ListingModuleListener {
	public static final String PLUGIN_ROOT = new File("plugins").getAbsolutePath();

	private AbstractPlugin activePlugin;
	private IExecutionModule executionModule;
	private PluginStatusModule pluginStatusModule;
	private String activePluginPath;

	public LifecycleController(IExecutionModule executionModule, PluginStatusModule pluginStatusModule) {
		this.executionModule = executionModule;
		this.pluginStatusModule = pluginStatusModule;
	}

	@Override
	public void startPlugin(String path) {
		AbstractPlugin plugin = importPlugin(path);
		if (plugin != null) {
			this.activePlugin = plugin;
			this.activePluginPath = path;

			pluginStatusModule.showActivePlugin("RUNNING", this.stopButtonListener, this.pauseButtonListener);
			plugin.onStart();
		} else {
			System.out.println("plugin \"" + path + "\" not found");
		}
	}

	public void stopPlugin(AbstractPlugin plugin) {
		plugin.onStop();
		pluginStatusModule.showInactivePlugin("STOPPED", this.playButtonListener);
		this.executionModule.clear();
		this.activePlugin = null;
	}

	public void pausePlugin(AbstractPlugin plugin) {
		pluginStatusModule.showPausedPlugin("PAUSED", this.resumeButtonListener);
		plugin.onPause();
	}

	public void resumePlugin(AbstractPlugin plugin) {
		pluginStatusModule.showActivePlugin("RUNNING", this.stopButtonListener, this.pauseButtonListener);
		plugin.onResume();
	}

	private AbstractPlugin importPlugin(String path) {
		JarClassLoader jarLoader = new JarClassLoader(PLUGIN_ROOT + "/" + path);
		/* Load the class from the jar file and resolve it. */
		Class<?> c;
		try {
			String className = path.substring(0, path.lastIndexOf('.'));
			c = (Class<?>) jarLoader.loadClass(className, true);
		} catch (ClassNotFoundException e1) {
			 e1.printStackTrace();
			return null;
		}
		/*
		 * Create an instance of the class.
		 * 
		 * Note that created object's constructor-taking-no-arguments will be
		 * called as part of the object's creation.
		 */
		Object o = null;
		try {
			o = c.getDeclaredConstructor(IExecutionModule.class).newInstance(executionModule);
		} catch (Exception e) {
			 e.printStackTrace();
			return null;
		}
		if (o instanceof AbstractPlugin) {
			AbstractPlugin plugin = (AbstractPlugin) o;
			return plugin;
		}
		return null;
	}

	private ActionListener playButtonListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			startPlugin(activePluginPath);
		}
	};

	private ActionListener stopButtonListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			stopPlugin(activePlugin);
		}
	};

	private ActionListener pauseButtonListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			pausePlugin(activePlugin);
		}
	};

	private ActionListener resumeButtonListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			resumePlugin(activePlugin);
		}
	};
}
