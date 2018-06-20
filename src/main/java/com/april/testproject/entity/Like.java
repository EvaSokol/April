package com.april.testproject.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Eva Sokolyanskaya on 19/06/2018.
 */
@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@Table(name = "likes")
public class Like {

	@Id
	@GeneratedValue
	Long id;

	Long userId;
	Long ideaId;
}
