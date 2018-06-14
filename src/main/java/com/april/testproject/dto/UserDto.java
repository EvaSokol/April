package com.april.testproject.dto;

import com.april.testproject.entity.UserRoleEnum;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class UserDto {
	private Long id;
	private String email;
	private String password;
	private String role;
	private String tags;
	private String firstName;
	private String lastName;
	private String avatarPicture;
	private String aboutUser;
	private String aboutCompany;
	private String country;
	private String city;

//	public String getTags() {
//		return tags;
//	}
//
//	public void setTags(String tags) {
//		this.tags = tags;
//	}
//
//	public String getAvatarPicture() {
//		return avatarPicture;
//	}
//
//	public void setAvatarPicture(String avatarPicture) {
//		this.avatarPicture = avatarPicture;
//	}
//
//	public String getAboutUser() {
//		return aboutUser;
//	}
//
//	public void setAboutUser(String aboutUser) {
//		this.aboutUser = aboutUser;
//	}
//
//	public String getAboutCompany() {
//		return aboutCompany;
//	}
//
//	public void setAboutCompany(String aboutCompany) {
//		this.aboutCompany = aboutCompany;
//	}
//
//	public String getCity() {
//		return city;
//	}
//
//	public void setCity(String city) {
//		this.city = city;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public String getLastName() {
//		return lastName;
//	}
//
//	public void setLastName(String lastName) {
//		this.lastName = lastName;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	public Long getId() {
//		return id;
//	}
//
//	public String getCountry() {
//		return country;
//	}
//
//	public void setCountry(String country) {
//		this.country = country;
//	}
//
//	public String getFirstName() {
//		return firstName;
//	}
//
//	public void setFirstName(String firstName) {
//		this.firstName = firstName;
//	}
//
//	public String getRole() {
//		return role;
//	}
//
//	public void setRole(String role) {
//		this.role = role;
//	}
//
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
}
