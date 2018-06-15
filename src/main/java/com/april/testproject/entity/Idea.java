package com.april.testproject.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.*;
import java.util.Date;
import java.util.Comparator;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
//@NamedQuery(name = "IdeaRepository.findByUserId", query = "SELECT i FROM ideas i WHERE i.user_id = ?1")
@Table(name = "ideas")
public class Idea implements Comparator<Idea> {

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
	@Type(type="timestamp")
	private Date creationDate;
	private int rate;

	public void print() {
		System.out.println("id:" + id);
		System.out.println("creationDate:" + creationDate);
		System.out.println("rate:" + rate);
		System.out.println("shortDescription:" + shortDescription);
		System.out.println("status:" + status);
		System.out.println("userId:" + userId);
		System.out.println("---------------------------");
	}

	@Override
	public int compare(Idea i1, Idea i2) {
		return i1.creationDate.compareTo(i2.creationDate);
	}

}
