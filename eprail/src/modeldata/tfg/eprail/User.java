package modeldata.tfg.eprail;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long uid;

	private Timestamp dateRegistration;

	private String email;

	private String familyName;

	private String firstName;

	private byte isValidate;

	private String password;
	
	@Transient
	private boolean loggedIn;

	//bi-directional many-to-one association to Deletedproject
	@OneToMany(mappedBy="user")
	private List<Deletedproject> deletedprojects;

	//bi-directional many-to-one association to Project
	@OneToMany(mappedBy="user")
	private List<Project> projects;

	//bi-directional many-to-one association to Sharing
	@OneToMany(mappedBy="user1")
	private List<Sharing> sharings1;

	//bi-directional many-to-one association to Sharing
	@OneToMany(mappedBy="user2")
	private List<Sharing> sharings2;

	public User() {
	}

	public long getUid() {
		return this.uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public Timestamp getDateRegistration() {
		return this.dateRegistration;
	}

	public void setDateRegistration(Timestamp dateRegistration) {
		this.dateRegistration = dateRegistration;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFamilyName() {
		return this.familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public byte getIsValidate() {
		return this.isValidate;
	}

	public void setIsValidate(byte isValidate) {
		this.isValidate = isValidate;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Deletedproject> getDeletedprojects() {
		return this.deletedprojects;
	}

	public void setDeletedprojects(List<Deletedproject> deletedprojects) {
		this.deletedprojects = deletedprojects;
	}
	
	public boolean getLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public Deletedproject addDeletedproject(Deletedproject deletedproject) {
		getDeletedprojects().add(deletedproject);
		deletedproject.setUser(this);

		return deletedproject;
	}

	public Deletedproject removeDeletedproject(Deletedproject deletedproject) {
		getDeletedprojects().remove(deletedproject);
		deletedproject.setUser(null);

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
		project.setUser(this);

		return project;
	}

	public Project removeProject(Project project) {
		getProjects().remove(project);
		project.setUser(null);

		return project;
	}

	public List<Sharing> getSharings1() {
		return this.sharings1;
	}

	public void setSharings1(List<Sharing> sharings1) {
		this.sharings1 = sharings1;
	}

	public Sharing addSharings1(Sharing sharings1) {
		getSharings1().add(sharings1);
		sharings1.setUser1(this);

		return sharings1;
	}

	public Sharing removeSharings1(Sharing sharings1) {
		getSharings1().remove(sharings1);
		sharings1.setUser1(null);

		return sharings1;
	}

	public List<Sharing> getSharings2() {
		return this.sharings2;
	}

	public void setSharings2(List<Sharing> sharings2) {
		this.sharings2 = sharings2;
	}

	public Sharing addSharings2(Sharing sharings2) {
		getSharings2().add(sharings2);
		sharings2.setUser2(this);

		return sharings2;
	}

	public Sharing removeSharings2(Sharing sharings2) {
		getSharings2().remove(sharings2);
		sharings2.setUser2(null);

		return sharings2;
	}

}