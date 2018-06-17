package com.april.testproject.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Eva Sokolyanskaya on 17/06/2018.
 */
@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
public class Tag {

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@NaturalId
	private String name;

//	@ManyToMany(fetch = FetchType.LAZY,
//					cascade = {
//									CascadeType.PERSIST,
//									CascadeType.MERGE
//					},
//					mappedBy = "tags")
//	private Set<Idea> ideas;
}
