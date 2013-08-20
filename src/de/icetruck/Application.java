package de.icetruck;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class Application extends JFrame implements WindowListener {
	private static Application app;
	private static List<User> userList;
	private List<String> chatList;
	private ChatListModel chatListModel;
	private Client client;
	private Thread clientThread;
	public static final int maxLineCount = 100;
	
	class User {
		private String name_;
		public User(String name) {
			name_ = name;
		}
		public String getName() {
			return name_;
		}
	}
	class UserListModel implements ListModel {
		private List<ListDataListener> listeners;

		UserListModel() {
			listeners = new LinkedList<ListDataListener>();
		}
		@Override
		public void addListDataListener(ListDataListener l) {
			listeners.add(l);
		}
		@Override
		public Object getElementAt(int index) {
			return userList.get(index);
		}
		@Override
		public int getSize() {
			return userList.size();
		}
		@Override
		public void removeListDataListener(ListDataListener l) {
			listeners.remove(l);
		}
	}
	class ChatListModel implements ListModel {
		private List<ListDataListener> listeners;

		ChatListModel() {
			listeners = new LinkedList<ListDataListener>();
		}
		@Override
		public void addListDataListener(ListDataListener l) {
			listeners.add(l);
		}
		@Override
		public Object getElementAt(int index) {
			return chatList.get(index);
		}
		@Override
		public int getSize() {
			return chatList.size();
		}
		@Override
		public void removeListDataListener(ListDataListener l) {
			listeners.remove(l);
		}
		public void add(String line) {
			chatList.add(line);
			Iterator<ListDataListener> i = listeners.iterator();
			while(i.hasNext()) {
				ListDataListener l = i.next();
				l.intervalAdded(new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, chatList.size()-1, chatList.size()-1));
			}
		}
		public void update() {
			Iterator<ListDataListener> i = listeners.iterator();
			while(i.hasNext()) {
				ListDataListener l = i.next();
				l.contentsChanged(new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, chatList.size()));
			}
		}
	}

	class SendView extends JTextField implements KeyListener {
		public SendView() {
			setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
			addKeyListener(this);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				String text = getText();
				setText("");
				client.sendMessage(text);
			}
		}
		@Override
		public void keyReleased(KeyEvent e) {}
		@Override
		public void keyTyped(KeyEvent e) {}
	}

	public synchronized void addChatLine(String line) {
		chatListModel.add(line);
	}

	public static Application getInstance() {
		return app;
	}

	Application() {
		chatList = new LinkedList<String>();
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		
		userList = new LinkedList<User>();
		JList userListView = new JList(new UserListModel());
		
		new JScrollPane(userListView);

		JPanel centerView = new JPanel();
		centerView.setLayout(new BoxLayout(centerView, BoxLayout.Y_AXIS));

		chatListModel = new ChatListModel();
		JList listView = new JList(chatListModel);
		JScrollPane listViewPanel = new JScrollPane(listView);
		centerView.add(listViewPanel);
		SendView sendView = new SendView();
		centerView.add(sendView);

		JSplitPane splitView = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, userListView, centerView);
		splitView.setDividerLocation(150);
		add(splitView);
		client = new Client();
		clientThread = new Thread(client);
		clientThread.start();
	}
	
	void run() {
		setVisible(true);
	}
	
	public static void main(String[] args) {
		app = new Application();
		app.run();
	}
	@Override
	public void windowActivated(WindowEvent e) { }

	@Override
	public void windowClosed(WindowEvent e) {
		client.setRunning(false);
	}

	@Override
	public void windowClosing(WindowEvent e) { }

	@Override
	public void windowDeactivated(WindowEvent e) { }

	@Override
	public void windowDeiconified(WindowEvent e) { }

	@Override
	public void windowIconified(WindowEvent e) { }

	@Override
	public void windowOpened(WindowEvent e) { }
}
