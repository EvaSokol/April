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
    private String name;

    private String country;

    private UserRoleEnum role;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;

    @OneToMany(cascade = CascadeType.ALL)
    public Set<Idea> ideaSet;

    public Set<Idea> getIdeaSet() {
        return ideaSet;
    }

    public void setIdeaSet(Set<Idea> ideaSet) {
        this.ideaSet = ideaSet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public UserRoleEnum getRole() {
        return role;
    }

    public void setRole(UserRoleEnum role) {
        this.role = role;
    }

    public void print(){
        System.out.println("id:" + id);
        System.out.println("name:" + name);
        System.out.println("country:" + country);
        System.out.println("role:" + role);
        System.out.println("---------------------------");
    }
}


