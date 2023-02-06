package dev.efrei.openefrei.managers.users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false, unique = true)
	private long efreiID;
	private boolean isAdmin;
	@Column(name= "first_name", nullable = false, length = 20)
	private String firstName;
	@Column(name="last_name", nullable = false, length = 20)
	private String lastName;
	@Column(nullable = false, unique = true, length = 45)
	private String email;

	public long getID() {
		return id;
	}
	
	public boolean isAdmin() {
		return isAdmin;
	}
	
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String fistname) {
		this.firstName = fistname;
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public long getEfreiID() {
		return efreiID;
	}

	public void setEfreiID(long efreiID) {
		this.efreiID = efreiID;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}