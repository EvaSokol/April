package com.april.testproject.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
public class IdeaUserNameDto {
    private Long id;
    private String status;
    private String tags;
    private Long userId;
    private String header;
    private String mainPicture;
    private String shortDescription;
    private String fullDescription;
    private String pictureList;
    private Date creationDate;
    private BigDecimal price;
    private Integer rate;
    private String firstName;
    private String lastName;
}
