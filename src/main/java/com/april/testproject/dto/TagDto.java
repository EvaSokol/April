package com.april.testproject.dto;

import com.april.testproject.entity.Idea;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * Created by Eva Sokolyanskaya on 17/06/2018.
 */
@Data
@Getter
@Setter
@NoArgsConstructor
public class TagDto {
	private Long id;
	private String name;
//	private Set<Long> ideaIds;
}
