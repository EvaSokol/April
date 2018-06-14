package com.april.testproject.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class IdeaDto {
	private Long id;
	private String status;
	private String tags;
	private String userId;
	private String header;
	private String mainPicture;
	private String shortDescription;
	private String fullDescription;
	private String pictureList;
	private String creationDate;
	private String rate;

//	public String getTags() {
//		return tags;
//	}
//
//	public void setTags(String tags) {
//		this.tags = tags;
//	}
//
//	public String getHeader() {
//		return header;
//	}
//
//	public void setHeader(String header) {
//		this.header = header;
//	}
//
//	public String getMainPicture() {
//		return mainPicture;
//	}
//
//	public void setMainPicture(String mainPicture) {
//		this.mainPicture = mainPicture;
//	}
//
//	public String getFullDescription() {
//		return fullDescription;
//	}
//
//	public void setFullDescription(String fullDescription) {
//		this.fullDescription = fullDescription;
//	}
//
//	public String getPictureList() {
//		return pictureList;
//	}
//
//	public void setPictureList(String pictureList) {
//		this.pictureList = pictureList;
//	}
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public String getShortDescription() {
//		return shortDescription;
//	}
//
//	public void setShortDescription(String shortDescription) {
//		this.shortDescription = shortDescription;
//	}
//
//	public String getStatus() {
//		return status;
//	}
//
//	public void setStatus(String status) {
//		this.status = status;
//	}
//
//	public String getUserId() {
//		return userId;
//	}
//
//	public void setUserId(String userId) {
//		this.userId = userId;
//	}
}
