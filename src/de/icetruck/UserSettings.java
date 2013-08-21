package de.icetruck;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class UserSettings extends JFrame implements WindowListener, UserNameChangeListener {
	private static UserSettings userSettings;

	public JTextField textUsername;
	public JPasswordField textPassword;

	private UserSettings() {
		setSize(350, 160);
		setMinimumSize(new Dimension(350, 160));
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		JPanel left = new JPanel();
		left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
		left.add(Box.createRigidArea(new Dimension(120, 20)));
		JLabel labelUsername = new JLabel("Username");
		labelUsername.setMinimumSize(new Dimension(120, 20));
		left.add(labelUsername);
		JLabel labelPassword = new JLabel("Password");
		labelPassword.setMinimumSize(new Dimension(200, 20));
		left.add(labelPassword);
		JPanel right = new JPanel();
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		right.add(Box.createRigidArea(new Dimension(200, 20)));
		textUsername = new JTextField();
		textUsername.setMaximumSize(new Dimension(200, 20));
		right.add(textUsername);
		textPassword = new JPasswordField();
		textPassword.setMaximumSize(new Dimension(200, 20));
		right.add(textPassword);
		JPanel center = new JPanel();
		center.setLayout(new BoxLayout(center, BoxLayout.X_AXIS));
		center.add(left);
		center.add(right);
		add(center);
		JButton buttonOk = new JButton("OK");
		buttonOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UserSettings.this.setEnabled(false);
				Application.getInstance().setUserName(UserSettings.this, textUsername.getText());
			}
		});
		add(buttonOk);
	}

	@Override
	public void onUserNameOk() {
		setEnabled(true);
		setVisible(false);
	}

	@Override
	public void onUserNameFailed() {
		JOptionPane.showMessageDialog(this, "Failed to change username.");
		setEnabled(true);
	}

	public synchronized static void showSettings() {
		if (userSettings == null)
			userSettings = new UserSettings();
		userSettings.setVisible(true);
	}

	@Override
	public void windowActivated(WindowEvent e) { }
	@Override
	public void windowClosed(WindowEvent e) { userSettings = null; }
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
