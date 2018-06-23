package com.april.testproject.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@Table(name = "ideas")
public class Idea implements Comparator<Idea> {

	@Id
	@GeneratedValue
	private Long id;

	private String status;

	@ManyToMany(fetch = FetchType.EAGER,
					cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "ideas_tags",
					joinColumns = @JoinColumn(name = "idea_id") ,
					inverseJoinColumns = @JoinColumn(name = "tag_id"),
					uniqueConstraints = @UniqueConstraint(columnNames={"idea_id", "tag_id"}))
	private Set<Tag> tags = new HashSet<>();

	@NotEmpty
	@Column(name = "user_id")
	private String userId;

	@NotEmpty
	private String header;

	private String mainPicture;
	private String shortDescription;
	private String fullDescription;
	private String pictureList;
	@Type(type="timestamp")
	private Date creationDate;
	private BigDecimal price;
	private Integer rate;

	public List<String> getTags(){
		List<String> list = new ArrayList<>();
		for (Tag tag : tags) list.add(tag.getName());
		return list;
	}

	public void print() {
		System.out.println("id:" + id);
		System.out.println("creationDate:" + creationDate);
		System.out.println("shortDescription:" + shortDescription);
		System.out.println("status:" + status);
		System.out.println("userId:" + userId);
		System.out.println("tags:" + getTags());
		System.out.println("header:" + header);
		System.out.println("rate: " + rate);
		System.out.println("---------------------------");
	}

	@Override
	public int compare(Idea i1, Idea i2) {
		return i1.creationDate.compareTo(i2.creationDate);
	}

}
