package de.icetruck;

public class Command {
	private String cmd_;
	private String subcmd_;
	private String line_;

	public Command(String cmd, String line) {
		int subcmdend = cmd.indexOf(":".charAt(0));
		subcmd_ = null;

		if (subcmdend >= 0) {
			subcmd_ = cmd.substring(subcmdend + 1);
			cmd_ = cmd.substring(0, subcmdend);
		} else {
			cmd_ = cmd;
		}
		line_ = line;
	}
	
	public String getCommand() {
		return cmd_;
	}
	public String getSubCommand() {
		return subcmd_;
	}
	public String getParamLine() {
		return line_;
	}
}
