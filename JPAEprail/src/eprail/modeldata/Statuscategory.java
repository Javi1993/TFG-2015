package eprail.modeldata;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the statuscategories database table.
 * 
 */
@Entity
@Table(name="statuscategories")
@NamedQuery(name="Statuscategory.findAll", query="SELECT s FROM Statuscategory s")
public class Statuscategory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private byte idProjectStatus;

	private String statusDescription;

	private String statusName;

	//bi-directional many-to-one association to Deletedproject
	@OneToMany(mappedBy="statuscategory")
	private List<Deletedproject> deletedprojects;

	//bi-directional many-to-one association to Project
	@OneToMany(mappedBy="statuscategory")
	private List<Project> projects;

	public Statuscategory() {
	}

	public byte getIdProjectStatus() {
		return this.idProjectStatus;
	}

	public void setIdProjectStatus(byte idProjectStatus) {
		this.idProjectStatus = idProjectStatus;
	}

	public String getStatusDescription() {
		return this.statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public String getStatusName() {
		return this.statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public List<Deletedproject> getDeletedprojects() {
		return this.deletedprojects;
	}

	public void setDeletedprojects(List<Deletedproject> deletedprojects) {
		this.deletedprojects = deletedprojects;
	}

	public Deletedproject addDeletedproject(Deletedproject deletedproject) {
		getDeletedprojects().add(deletedproject);
		deletedproject.setStatuscategory(this);

		return deletedproject;
	}

	public Deletedproject removeDeletedproject(Deletedproject deletedproject) {
		getDeletedprojects().remove(deletedproject);
		deletedproject.setStatuscategory(null);

		return deletedproject;
	}

	public List<Project> getProjects() {
		return this.projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public Project addProject(Project project) {
		getProjects().add(project);
		project.setStatuscategory(this);

		return project;
	}

	public Project removeProject(Project project) {
		getProjects().remove(project);
		project.setStatuscategory(null);

		return project;
	}

}