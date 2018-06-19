package com.april.testproject.repository;

import com.april.testproject.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Eva Sokolyanskaya on 19/06/2018.
 */
public interface LikeRepository extends JpaRepository<Like, Long> {

	@Query(value = "SELECT COUNT(l) FROM Like l WHERE l.ideaId = ?1")
	int getLikesOfIdea(Long ideaId);

}
