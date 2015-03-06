package eprail.modeldata;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the sharings database table.
 * 
 */
@Entity
@Table(name="sharings")
@NamedQuery(name="Sharing.findAll", query="SELECT s FROM Sharing s")
public class Sharing implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idSharing;

	private byte allowDelete;

	private byte allowDownload;

	private byte allowRecalculate;

	private byte allowShare;

	private Timestamp dateChanged;

	private Timestamp dateShared;

	//bi-directional many-to-one association to Project
	@ManyToOne
	@JoinColumn(name="IdProject")
	private Project project;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="UID")
	private User user1;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="UIDsharer")
	private User user2;

	public Sharing() {
	}

	public long getIdSharing() {
		return this.idSharing;
	}

	public void setIdSharing(long idSharing) {
		this.idSharing = idSharing;
	}

	public byte getAllowDelete() {
		return this.allowDelete;
	}

	public void setAllowDelete(byte allowDelete) {
		this.allowDelete = allowDelete;
	}

	public byte getAllowDownload() {
		return this.allowDownload;
	}

	public void setAllowDownload(byte allowDownload) {
		this.allowDownload = allowDownload;
	}

	public byte getAllowRecalculate() {
		return this.allowRecalculate;
	}

	public void setAllowRecalculate(byte allowRecalculate) {
		this.allowRecalculate = allowRecalculate;
	}

	public byte getAllowShare() {
		return this.allowShare;
	}

	public void setAllowShare(byte allowShare) {
		this.allowShare = allowShare;
	}

	public Timestamp getDateChanged() {
		return this.dateChanged;
	}

	public void setDateChanged(Timestamp dateChanged) {
		this.dateChanged = dateChanged;
	}

	public Timestamp getDateShared() {
		return this.dateShared;
	}

	public void setDateShared(Timestamp dateShared) {
		this.dateShared = dateShared;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public User getUser1() {
		return this.user1;
	}

	public void setUser1(User user1) {
		this.user1 = user1;
	}

	public User getUser2() {
		return this.user2;
	}

	public void setUser2(User user2) {
		this.user2 = user2;
	}

}