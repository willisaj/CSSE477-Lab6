package edu.rosehulman.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;

import edu.rosehulman.platform.LifecycleController;
import edu.rosehulman.platform.PluginApp;

public class ListingModule extends JPanel {
	private static final int MIN_HEIGHT = 800;
	private static final int MIN_WIDTH = 300;
	private static final long serialVersionUID = 8188626098543021658L;
	private static final KeyStroke ENTER = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);

	private final DefaultListModel<String> listModel;
	private final WatchService watcher;
	private Dimension size;
	private ListingModuleListener listener;

	@SuppressWarnings("serial")
	public ListingModule(String pathString, LifecycleController lifecycleController) throws IOException {
		size = new Dimension(MIN_WIDTH, MIN_HEIGHT);
		listModel = new DefaultListModel<>();
		this.listener = lifecycleController;

		// Watch for directory changes
		watcher = FileSystems.getDefault().newWatchService();
		Path path = Paths.get(URI.create(pathString.replace("\\", "//").replace(" ", "%20")));
		path.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);
		
		List<String> files = new ArrayList<>();
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
		    for (Path file : stream) {
		    	files.add(file.getFileName().toString());
		    }
		} catch (IOException | DirectoryIteratorException x) {
		    System.err.println(x);
		}
		
		Collections.sort(files);
		
		for (String file : files) {
			listModel.addElement(file);
		}

		// Initialize and display list
		final JList<String> listView = new JList<>(listModel);
		listView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		// This adds the listener to the listView
		new ListAction(listView, new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Running plugin \"" + listView.getSelectedValue() + "\"");
				ListingModule.this.listener.startPlugin(listView.getSelectedValue());
			}
		});

		this.setSize(size);
		this.setMinimumSize(size);
		this.setLayout(new GridLayout(1, 1));
		this.setBorder(BorderFactory.createTitledBorder("Available Plugins"));
		
		JScrollPane scrollPane = new JScrollPane(listView);
		
		this.add(scrollPane);
		this.setVisible(true);

		// Listen asynchronously for directory changes
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					listenForDirectoryChanges();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}, "DirectoryWatcher").start();
	}

	
	
	private void listenForDirectoryChanges() throws IOException {
		for (;;) {
			WatchKey key;
			try {
				key = watcher.take();
			} catch (InterruptedException x) {
				return;
			}

			for (WatchEvent<?> event : key.pollEvents()) {
				WatchEvent.Kind<?> kind = event.kind();

				if (kind == StandardWatchEventKinds.OVERFLOW) {
					continue;
				}

				@SuppressWarnings("unchecked")
				WatchEvent<Path> ev = (WatchEvent<Path>) event;
				String filename = ev.context().toFile().getName();
				
				// Ignore if it's not a JAR
				if (!filename.endsWith(".jar")) {
					continue;
				}

				if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
					listModel.addElement(filename);
				} else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
					listModel.removeElement(filename);
				}
			}
			
			if (!key.reset()) {
				throw new IOException("Plugin file is no longer accessible");
			}
		}
	}
	
	/**
	 * Main method for testing listing module
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		JFrame window = new JFrame("ListModule test");
		window.setSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
		ListingModule module = new ListingModule("file:///" + LifecycleController.PLUGIN_ROOT, null);
		window.add(module);
		window.setVisible(true);
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Allows a JList to respond to double-click and Enter on its selected item.
	 * 
	 * Borrowed from the URL below. Assumed open-source.
	 * 
	 * @see https://tips4java.wordpress.com/2008/10/14/list-action/
	 *
	 */
	@SuppressWarnings("rawtypes")
	private class ListAction implements MouseListener {
		private JList list;
		private KeyStroke keyStroke;

		/*
		 * Add an Action to the JList bound by the default KeyStroke
		 */
		public ListAction(JList list, Action action) {
			this(list, action, ENTER);
		}

		/*
		 * Add an Action to the JList bound by the specified KeyStroke
		 */
		public ListAction(JList list, Action action, KeyStroke keyStroke) {
			this.list = list;
			this.keyStroke = keyStroke;

			// Add the KeyStroke to the InputMap

			InputMap im = list.getInputMap();
			im.put(keyStroke, keyStroke);

			// Add the Action to the ActionMap

			setAction(action);

			// Handle mouse double click

			list.addMouseListener(this);
		}

		/*
		 * Add the Action to the ActionMap
		 */
		public void setAction(Action action) {
			list.getActionMap().put(keyStroke, action);
		}

		// Implement MouseListener interface

		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				Action action = list.getActionMap().get(keyStroke);

				if (action != null) {
					ActionEvent event = new ActionEvent(list, ActionEvent.ACTION_PERFORMED, "");
					action.actionPerformed(event);
				}
			}
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}
	}
}
