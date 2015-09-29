package edu.rosehulman.platform;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.util.Map;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import edu.rosehulman.gui.IExecutionModule;
import edu.rosehulman.gui.PluginStatusModule;
import edu.rosehulman.plugin.AbstractPlugin;

public class LifecycleController implements ListDataListener {
	private Map<String, AbstractPlugin> installedPlugins;
	private AbstractPlugin activePlugin;
	
	private IExecutionModule executionPanel;
	private PluginStatusModule pluginStatusModule;

	public LifecycleController(IExecutionModule executionPanel, PluginStatusModule pluginStatusModule) {
		this.executionPanel = executionPanel;
		this.pluginStatusModule = pluginStatusModule;
	}

	public void startPlugin(AbstractPlugin plugin) {
		pluginStatusModule.showActivePlugin("TODO", this.stopButtonListener, this.pauseButtonListener);
		plugin.onStart();
	}

	public void stopPlugin(AbstractPlugin plugin) {
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
		JarClassLoader jarLoader = new JarClassLoader(PluginManager.PLUGIN_ROOT
				+ "/" + path);
		/* Load the class from the jar file and resolve it. */
		Class c;
		try {
			c = (Class<AbstractPlugin>) jarLoader.loadClass(
					AbstractPlugin.class.getName(), true);
		} catch (ClassNotFoundException e1) {
			System.err.println("Loading class failed");
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
			Constructor ctor = c.getDeclaredConstructor(IExecutionModule.class);
			o = ctor.newInstance(executionPanel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (o instanceof AbstractPlugin) {
			AbstractPlugin plugin = (AbstractPlugin) o;
			this.installedPlugins.put(path, plugin);
		}
	}

	public void deletePlugin(String path) {

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
	public void intervalRemoved(ListDataEvent arg0) {

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
