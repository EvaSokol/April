package com.april.testproject.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
//@Data
//@Getter
//@Setter
//@NoArgsConstructor
@Table(name = "users")
public class User implements Serializable {

	@Id
	@GeneratedValue
	private Long id;

	@NotEmpty
	private String email;

	@NotEmpty
	private String password;

	private String role;
	private String tags;

	@NotEmpty
	private String firstName;
	private String lastName;
	private String avatarPicture;
	private String aboutUser;
	private String aboutCompany;
	private String country;
	private String city;

	public void print() {
		System.out.println("id:" + id);
		System.out.println("email:" + email);
		System.out.println("firstName:" + firstName);
		System.out.println("country:" + country);
		System.out.println("role:" + role);
		System.out.println("---------------------------");
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAvatarPicture() {
		return avatarPicture;
	}

	public void setAvatarPicture(String avaterPicture) {
		this.avatarPicture = avaterPicture;
	}

	public String getAboutUser() {
		return aboutUser;
	}

	public void setAboutUser(String aboutUser) {
		this.aboutUser = aboutUser;
	}

	public String getAboutCompany() {
		return aboutCompany;
	}

	public void setAboutCompany(String aboutCompany) {
		this.aboutCompany = aboutCompany;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getId() {
		return id;
	}

//	public void setId(Long id) {
//		this.id = id;
//	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}


