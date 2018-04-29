package com.april.testproject.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
//@NamedQuery(name = "IdeasRepository.findByUserId", query = "SELECT i FROM ideas i WHERE i.user_id = ?1")
@Table(name = "ideas")
public class Idea {

    @Id
    @GeneratedValue
    private Long id;

    private String short_description;

    private String status;

    @Column(name = "user_id")
    private String userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
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
}
