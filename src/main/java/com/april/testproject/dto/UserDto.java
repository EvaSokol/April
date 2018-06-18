package com.april.testproject.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

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
	private Date regDate;

}
