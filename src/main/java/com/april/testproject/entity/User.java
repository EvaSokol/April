package com.april.testproject.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
//@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String name;

//    @OneToOne(cascade = CascadeType.ALL)
    private String country;

//    @OneToOne(cascade = CascadeType.ALL)
    private String role;

    @OneToMany(cascade = CascadeType.ALL)
    public Set<Idea> ideaSet;

    public Set<Idea> getIdeaSet() {
        return ideaSet;
    }

    public void setIdeaSet(Set<Idea> ideaSet) {
        this.ideaSet = ideaSet;
    }


//    public User(String name) {
//        this.name = name;
//    }


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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
