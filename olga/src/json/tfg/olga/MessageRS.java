package json.tfg.olga;

public class MessageRS {
	private String numSeq;
	private String parameter;
	
	public MessageRS() {
		super();
	}
	
	public MessageRS(String numSeq, String parameter) {
		super();
		this.setNumSeq(numSeq);
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
