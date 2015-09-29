package edu.rosehulman.platform;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import edu.rosehulman.gui.IExecutionModule;
import edu.rosehulman.gui.ListingModuleListener;
import edu.rosehulman.gui.PluginStatusModule;
import edu.rosehulman.plugin.AbstractPlugin;

public class LifecycleController implements ListDataListener, ListingModuleListener {
	private Map<String, AbstractPlugin> installedPlugins;
	private AbstractPlugin activePlugin;

	private IExecutionModule executionModule;
	private PluginStatusModule pluginStatusModule;

	public LifecycleController(IExecutionModule executionModule, PluginStatusModule pluginStatusModule) {
		this.executionModule = executionModule;
		this.pluginStatusModule = pluginStatusModule;
		this.installedPlugins = new HashMap<>();
	}

	public void startPlugin(AbstractPlugin plugin) {
		pluginStatusModule.showActivePlugin("TODO", this.stopButtonListener, this.pauseButtonListener);
		plugin.onStart();
	}

	@Override
	public void startPlugin(String path) {
		AbstractPlugin plugin = this.installedPlugins.get(path);
		if (plugin != null) {
			this.activePlugin = plugin;
			startPlugin(plugin);
		} else {
			System.err.println("plugin not found");
		}
	}

	public void stopPlugin(AbstractPlugin plugin) {
		this.executionModule.clear();
		pluginStatusModule.showInactivePlugin("TODO", this.playButtonListener);
		plugin.onStop();
	}

	public void pausePlugin(AbstractPlugin plugin) {
		pluginStatusModule.showPausedPlugin("TODO", this.resumeButtonListener);
		plugin.onPause();

	}

	public void resumePlugin(AbstractPlugin plugin) {
		pluginStatusModule.showActivePlugin("TODO", this.stopButtonListener, this.pauseButtonListener);
		plugin.onResume();
	}

	private void importPlugin(String path) {
		JarClassLoader jarLoader = new JarClassLoader(PluginManager.PLUGIN_ROOT + "/" + path);
		/* Load the class from the jar file and resolve it. */
		Class<?> c;
		try {
			String className = path.substring(0, path.lastIndexOf('.'));
			c = (Class<?>) jarLoader.loadClass(className, true);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
			return;
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
		}
		if (o instanceof AbstractPlugin) {
			AbstractPlugin plugin = (AbstractPlugin) o;
			this.installedPlugins.put(path, plugin);
		}
	}

	public void deletePlugin(String path) {
		if (!(this.installedPlugins.get(path) == this.activePlugin)) {
			this.installedPlugins.remove(path);
		}
	}

	@Override
	public void contentsChanged(ListDataEvent ignored) {
		// unused
	}

	@Override
	public void intervalAdded(ListDataEvent event) {
		ListModel<String> listModel = (ListModel<String>) event.getSource();
		String path = listModel.getElementAt(event.getIndex0());
		importPlugin(path);
	}

	@Override
	public void intervalRemoved(ListDataEvent event) {
		ListModel<String> listModel = (ListModel<String>) event.getSource();
		String path = listModel.getElementAt(event.getIndex0());
		deletePlugin(path);
	}

	private ActionListener playButtonListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			startPlugin(activePlugin);
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
