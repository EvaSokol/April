package com.april.testproject.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
//@NamedQuery(name = "IdeaRepository.findByUserId", query = "SELECT i FROM ideas i WHERE i.user_id = ?1")
@Table(name = "ideas")
public class Idea {

    @Id
    @GeneratedValue
    private Long id;

    private String shortDescription;

    private String status;

    @Column(name = "user_id")
    private String userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void print(){
        System.out.println("id:" + id);
        System.out.println("shortDescription:" + shortDescription);
        System.out.println("status:" + status);
        System.out.println("userId:" + userId);
        System.out.println("---------------------------");
    }
}
