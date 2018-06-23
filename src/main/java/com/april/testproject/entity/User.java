package com.april.testproject.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
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
//	private String tags;

	@NotEmpty
	private String firstName;
	private String lastName;
	private String avatarPicture;
	private String aboutUser;
	private String aboutCompany;
	private String country;
	private String city;
	private Date regDate;

	public void print() {
		System.out.println("id:" + id);
		System.out.println("email:" + email);
		System.out.println("firstName:" + firstName);
		System.out.println("country:" + country);
		System.out.println("role:" + role);
		System.out.println("password:" + password);
		System.out.println("---------------------------");
	}


}


