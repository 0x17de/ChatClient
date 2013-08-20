package de.icetruck;

import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

public class Application extends JFrame {
	public static Application app;
	public static List<User> userList;

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

	Application() {
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

		userList = new LinkedList<User>();
		JList userListView = new JList(new UserListModel());
		
		new JScrollPane(userListView);

		JPanel centerView = new JPanel();
		centerView.setLayout(new BoxLayout(centerView, BoxLayout.Y_AXIS));
		String[] a = {"A long text", "More text"};
		JList listView = new JList(a);
		JScrollPane listViewPanel = new JScrollPane(listView);
		centerView.add(listViewPanel);
		JTextField textView = new JTextField();
		textView.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
		centerView.add(textView);

		JSplitPane splitView = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, userListView, centerView);
		splitView.setDividerLocation(150);
		add(splitView);
	}
	
	void run() {
		setVisible(true);
	}
	
	public static void main(String[] args) {
		app = new Application();
		app.run();
	}
}
