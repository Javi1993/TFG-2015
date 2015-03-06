package javabeans.tfg.eprail;

import java.io.Serializable;
import java.sql.Timestamp;

public class Sharings implements Serializable{
	private static final long serialVersionUID = 1L;

	private int idSharing;
	private Project idProject;
	private User uid;
	private User uidSharer;
	private Timestamp dateShared;
	private Timestamp dateChanged;
	private byte allowRecalculated;
	private byte allowDelete;
	private byte allowDownload;
	private byte allowShare;

	public Sharings(int idSharing, Project idProject, User uid, User uidSharer, Timestamp dateShared, Timestamp dateChanged, byte allowRecalculated, byte allowDelete, byte allowDownload, byte allowShare){
		this.setIdSharing(idSharing);
		this.setIdProject(idProject);
		this.setUid(uid);
		this.setUidSharer(uidSharer);
		this.setDateShared(dateShared);
		this.setDateChanged(dateChanged);
		this.setAllowRecalculated(allowRecalculated);
		this.setAllowDelete(allowDelete);
		this.setAllowDownload(allowDownload);
		this.setAllowShare(allowShare);
	}

	/**
	 * @return the idSharing
	 */
	public int getIdSharing() {
		return idSharing;
	}
	/**
	 * @param idSharing the idSharing to set
	 */
	public void setIdSharing(int idSharing) {
		this.idSharing = idSharing;
	}
	/**
	 * @return the idProject
	 */
	public Project getIdProject() {
		return idProject;
	}
	/**
	 * @param idProject the idProject to set
	 */
	public void setIdProject(Project idProject) {
		this.idProject = idProject;
	}
	/**
	 * @return the uid
	 */
	public User getUid() {
		return uid;
	}
	/**
	 * @param uid the uid to set
	 */
	public void setUid(User uid) {
		this.uid = uid;
	}
	/**
	 * @return the uidSharer
	 */
	public User getUidSharer() {
		return uidSharer;
	}
	/**
	 * @param uidSharer the uidSharer to set
	 */
	public void setUidSharer(User uidSharer) {
		this.uidSharer = uidSharer;
	}
	/**
	 * @return the dateCreation
	 */
	public Timestamp getShred() {
		return dateShared;
	}
	/**
	 * @param dateCreation the dateCreation to set
	 */
	public void setDateShared(Timestamp dateShared) {
		this.dateShared = dateShared;
	}
	/**
	 * @return the dateChanged
	 */
	public Timestamp getDateChanged() {
		return dateChanged;
	}
	/**
	 * @param dateChanged the dateChanged to set
	 */
	public void setDateChanged(Timestamp dateChanged) {
		this.dateChanged = dateChanged;
	}
	/**
	 * @return the allowRecalculated
	 */
	public byte isAllowRecalculated() {
		return allowRecalculated;
	}
	/**
	 * @param allowRecalculated the allowRecalculated to set
	 */
	public void setAllowRecalculated(byte allowRecalculated) {
		this.allowRecalculated = allowRecalculated;
	}
	/**
	 * @return the allowDelete
	 */
	public byte isAllowDelete() {
		return allowDelete;
	}
	/**
	 * @param allowDelete the allowDelete to set
	 */
	public void setAllowDelete(byte allowDelete) {
		this.allowDelete = allowDelete;
	}
	/**
	 * @return the allowDownload
	 */
	public byte isAllowDownload() {
		return allowDownload;
	}
	/**
	 * @param allowDownload the allowDownload to set
	 */
	public void setAllowDownload(byte allowDownload) {
		this.allowDownload = allowDownload;
	}
	/**
	 * @return the allowShare
	 */
	public byte isAllowShare() {
		return allowShare;
	}
	/**
	 * @param allowShare the allowShare to set
	 */
	public void setAllowShare(byte allowShare) {
		this.allowShare = allowShare;
	}
}
