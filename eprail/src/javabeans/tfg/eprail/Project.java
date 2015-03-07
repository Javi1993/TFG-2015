package javabeans.tfg.eprail;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Timestamp;

public class Project implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int idProject;
	private String projectName;
	private String projectDescription;
	private Blob ONGFile;
	private User uid;
	private Status idProjectStatus;
	private Timestamp dateCreation;
	private Timestamp dateModifed;

	public Project(int idProject, String projectName, String projectDescription, Blob ONGFile, User uid, Status idProjectStatus, Timestamp dateCreation, Timestamp dateModifed){

		this.setIdProject(idProject);
		this.setProjectName(projectName);
		this.setProjectDescription(projectDescription);
		this.setONGFile(ONGFile);
		this.setUid(uid);
		this.setIdProjectStatus(idProjectStatus);
		this.setDateCreation(dateCreation);
		this.setDateModifed(dateModifed);

	}

	/**
	 * @return the idProject
	 */
	public int getIdProject() {
		return idProject;
	}
	/**
	 * @param idProject the idProject to set
	 */
	public void setIdProject(int idProject) {
		this.idProject = idProject;
	}
	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}
	/**
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	/**
	 * @return the projectDescription
	 */
	public String getProjectDescription() {
		return projectDescription;
	}
	/**
	 * @param projectDescription the projectDescription to set
	 */
	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}
	/**
	 * @return the oNGFile
	 */
	public Blob getONGFile() {
		return ONGFile;
	}
	/**
	 * @param oNGFile the oNGFile to set
	 */
	public void setONGFile(Blob oNGFile) {
		ONGFile = oNGFile;
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
	 * @return the idProjectStatus
	 */
	public Status getIdProjectStatus() {
		return idProjectStatus;
	}

	/**
	 * @param idProjectStatus the idProjectStatus to set
	 */
	public void setIdProjectStatus(Status idProjectStatus) {
		this.idProjectStatus = idProjectStatus;
	}
	/**
	 * @return the dateCration
	 */
	public Timestamp getDateCreation() {
		return dateCreation;
	}
	/**
	 * @param dateCration the dateCration to set
	 */
	public void setDateCreation(Timestamp dateCreation) {
		this.dateCreation = dateCreation;
	}
	/**
	 * @return the dateModifed
	 */
	public Timestamp getDateModifed() {
		return dateModifed;
	}
	/**
	 * @param dateModifed the dateModifed to set
	 */
	public void setDateModifed(Timestamp dateModifed) {
		this.dateModifed = dateModifed;
	}

}