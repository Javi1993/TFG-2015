package modeldata.tfg.eprail;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the projects database table.
 * 
 */
@Entity
@Table(name="projects")
@NamedQuery(name="Project.findAll", query="SELECT p FROM Project p")
public class Project implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idProject;

	private Timestamp dateCreation;

	private Timestamp dateModified;

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

	//bi-directional many-to-one association to Sharing
	@OneToMany(mappedBy="project")
	private List<Sharing> sharings;

	public Project() {
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

	public Timestamp getDateModified() {
		return this.dateModified;
	}

	public void setDateModified(Timestamp dateModified) {
		this.dateModified = dateModified;
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

	public List<Sharing> getSharings() {
		return this.sharings;
	}

	public void setSharings(List<Sharing> sharings) {
		this.sharings = sharings;
	}

	public Sharing addSharing(Sharing sharing) {
		getSharings().add(sharing);
		sharing.setProject(this);

		return sharing;
	}

	public Sharing removeSharing(Sharing sharing) {
		getSharings().remove(sharing);
		sharing.setProject(null);

		return sharing;
	}

}