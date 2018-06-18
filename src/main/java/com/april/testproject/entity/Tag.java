package com.april.testproject.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

}
