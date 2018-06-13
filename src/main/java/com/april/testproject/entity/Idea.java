package com.april.testproject.entity;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Data
//@NamedQuery(name = "IdeaRepository.findByUserId", query = "SELECT i FROM ideas i WHERE i.user_id = ?1")
@Table(name = "ideas")
public class Idea {

	@Id
	@GeneratedValue
	private Long id;

	private String status;
	private String tags;

	@NotEmpty
	@Column(name = "user_id")
	private String userId;

	@NotEmpty
	private String header;

	private String mainPicture;
	private String shortDescription;
	private String fullDescription;
	private String pictureList;

	public void print() {
		System.out.println("id:" + id);
		System.out.println("shortDescription:" + shortDescription);
		System.out.println("status:" + status);
		System.out.println("userId:" + userId);
		System.out.println("---------------------------");
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getMainPicture() {
		return mainPicture;
	}

	public void setMainPicture(String mainPicture) {
		this.mainPicture = mainPicture;
	}

	public String getFullDescription() {
		return fullDescription;
	}

	public void setFullDescription(String fullDescription) {
		this.fullDescription = fullDescription;
	}

	public String getPictureList() {
		return pictureList;
	}

	public void setPictureList(String pictureList) {
		this.pictureList = pictureList;
	}

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


}
