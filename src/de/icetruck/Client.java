package de.icetruck;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class Client implements Runnable {
	private Socket s_;
	private boolean running_;
	
	Client() {
		running_ = true;
	}

	public void setRunning(boolean running) {
		running_ = running;
	}
	
	public void sendMessage(String msg) {
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(s_.getOutputStream()));
			writer.write("MSG " + msg.replace(";", "\\;") + ";");			
			writer.flush();
		} catch(IOException e) {
			return;
		}
	}
	
	@Override
	public void run() {
		Application.getInstance().addChatLine("Connecting...");
		try {
			s_ = new Socket("localhost", 12321);
		} catch(UnknownHostException e) {
			JOptionPane.showMessageDialog(Application.getInstance(), "Unknown host.");
			return;
		} catch(IOException e) {
			JOptionPane.showMessageDialog(Application.getInstance(), "Could not connect to server.");
			return;
		}
		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(s_.getInputStream()));
			// writer = new BufferedWriter(new OutputStreamWriter(s_.getOutputStream()));
		} catch(IOException e) {
			JOptionPane.showMessageDialog(Application.getInstance(), "Broken pipe");
			return;
		}
		while(running_) {
			try {
				String line = reader.readLine();
				Application.getInstance().addChatLine(line);
				
				int cmdend = line.indexOf(" ".charAt(0));
				int subcmdend = line.indexOf(":".charAt(0));
				if (cmdend < 0)
					return;
				int firstbr = (subcmdend >= 0 && subcmdend < cmdend) ? subcmdend : cmdend;
				String cmd = line.substring(0, firstbr);
				Application.getInstance().addChatLine(cmd);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(Application.getInstance(), "Broken pipe");
				return;
			}
		}
	}
}
