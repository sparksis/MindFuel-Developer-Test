package ca.mindfuel.test.colton.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "IMAGES", uniqueConstraints = { @UniqueConstraint(columnNames = { "FILENAME", "USER_USERNAME" }) })
public class Image implements IdentifiableEntity<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(referencedColumnName = "username")
	private User user;

	private String filename;

	@Basic
	@Lob
	private byte[] filedata;
	public byte[] getFiledata() {
		return filedata;
	}

	public String getFilename() {
		return filename;
	}

	@Override
	public Long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public void setFiledata(byte[] filedata) {
		this.filedata = filedata;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
