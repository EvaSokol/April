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
    private String firstName;

    @NotEmpty
    private String email;

    private String country;

    private String role;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;

//    @OneToMany(cascade = CascadeType.ALL)
//    public Set<Idea> ideaSet;

//    public Set<Idea> getIdeaSet() {
//        return ideaSet;
//    }

//    public void setIdeaSet(Set<Idea> ideaSet) {
//        this.ideaSet = ideaSet;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public void print(){
        System.out.println("id:" + id);
	      System.out.println("email:" + email);
        System.out.println("firstName:" + firstName);
        System.out.println("country:" + country);
        System.out.println("role:" + role);
        System.out.println("---------------------------");
    }
}


