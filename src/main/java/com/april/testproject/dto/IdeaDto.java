package com.april.testproject.dto;

import com.april.testproject.entity.Tag;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	private Date creationDate;
	private int rate;
	private BigDecimal price;
	private String whoLiked;

}
