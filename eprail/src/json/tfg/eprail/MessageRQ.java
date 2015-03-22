package json.tfg.eprail;

public class MessageRQ {
	private String numSeq;
	private String command;
	private String parameter;
	
	public MessageRQ() {
		super();
	}
	
	public MessageRQ(String numSeq, String command, String parameter) {
		super();
		this.setNumSeq(numSeq);
		this.setCommand(command);
		this.setParameter(parameter);
	}

	/**
	 * @return the numSeq
	 */
	public String getNumSeq() {
		return numSeq;
	}

	/**
	 * @param numSeq the numSeq to set
	 */
	public void setNumSeq(String numSeq) {
		this.numSeq = numSeq;
	}

	/**
	 * @return the command
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * @param command the command to set
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * @return the parameter
	 */
	public String getParameter() {
		return parameter;
	}

	/**
	 * @param parameter the parameter to set
	 */
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
}
