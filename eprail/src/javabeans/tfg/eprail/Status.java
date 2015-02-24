package javabeans.tfg.eprail;

import java.io.Serializable;

public class Status implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private byte idProjectStatus;
	private String statusName;
	private String statusDescription;
	
	public Status(byte idProjectStatus, String statusName, String statusDescription){
		this.setIdProjectStatus(idProjectStatus);
		this.setStatusName(statusName);
		this.setStatusDescription(statusDescription);
	}
	
	/**
	 * @return the idProjectStatus
	 */
	public byte getIdProjectStatus() {
		return idProjectStatus;
	}
	/**
	 * @param idProjectStatus the idProjectStatus to set
	 */
	public void setIdProjectStatus(byte idProjectStatus) {
		this.idProjectStatus = idProjectStatus;
	}
	/**
	 * @return the statusName
	 */
	public String getStatusName() {
		return statusName;
	}
	/**
	 * @param statusName the statusName to set
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	/**
	 * @return the statusDescription
	 */
	public String getStatusDescription() {
		return statusDescription;
	}
	/**
	 * @param statusDescription the statusDescription to set
	 */
	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}
}
