package edu.rosehulman.platform;

import java.lang.reflect.Constructor;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import edu.rosehulman.plugin.AbstractPlugin;

public class LifecycleControl implements ListDataListener {
	private Map<String, AbstractPlugin> installedPlugins;
	private JPanel executionPanel;

	public LifecycleControl(JPanel executionPanel) {
		this.executionPanel = executionPanel;
	}
	
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

	private void importPlugin(String path) {
		JarClassLoader jarLoader = new JarClassLoader(PluginManager.PLUGIN_ROOT + "/" + path);
		/* Load the class from the jar file and resolve it. */
		Class c;
		try {
			c = (Class<AbstractPlugin>) jarLoader.loadClass(AbstractPlugin.class.getName(), true);
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
			Constructor ctor = c.getDeclaredConstructor(JPanel.class);
			o = ctor.newInstance(executionPanel);
		} catch (Exception e) {
			System.err.println("Failed to load class: " + e);
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
}
