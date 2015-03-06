package eprail.modeldata;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the deletedprojects database table.
 * 
 */
@Entity
@Table(name="deletedprojects")
@NamedQuery(name="Deletedproject.findAll", query="SELECT d FROM Deletedproject d")
public class Deletedproject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idProject;

	private Timestamp dateCreation;

	private Timestamp dateDeleted;

	@Lob
	private byte[] ONGFile;

	private String projectDescription;

	private String projectName;

	//bi-directional many-to-one association to Statuscategory
	@ManyToOne
	@JoinColumn(name="IdProjectStatus")
	private Statuscategory statuscategory;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="UID")
	private User user;

	public Deletedproject() {
	}

	public long getIdProject() {
		return this.idProject;
	}

	public void setIdProject(long idProject) {
		this.idProject = idProject;
	}

	public Timestamp getDateCreation() {
		return this.dateCreation;
	}

	public void setDateCreation(Timestamp dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Timestamp getDateDeleted() {
		return this.dateDeleted;
	}

	public void setDateDeleted(Timestamp dateDeleted) {
		this.dateDeleted = dateDeleted;
	}

	public byte[] getONGFile() {
		return this.ONGFile;
	}

	public void setONGFile(byte[] ONGFile) {
		this.ONGFile = ONGFile;
	}

	public String getProjectDescription() {
		return this.projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Statuscategory getStatuscategory() {
		return this.statuscategory;
	}

	public void setStatuscategory(Statuscategory statuscategory) {
		this.statuscategory = statuscategory;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}