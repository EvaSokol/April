package com.april.testproject.repository;

import com.april.testproject.entity.Idea;
import com.april.testproject.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Eva Sokolyanskaya on 19/06/2018.
 */
public interface LikeRepository extends JpaRepository<Like, Long> {

	@Query(value = "SELECT COUNT(l) FROM Like l WHERE l.ideaId = ?1")
	int getLikesOfIdea(Long ideaId);

//	@Query(value = "SELECT DISTINCT l.ideaId FROM Like l ORDER BY COUNT(userId)")
//	List<Like> getIdeasSortedByLikes();

	@Query(value = "SELECT DISTINCT l.ideaId FROM Like l")
	List<Long> getLikedIdeas();

	@Query(value = "SELECT l FROM Like l WHERE l.userId = ?1 AND l.ideaId = ?2")
	List<Like> findLike(Long userId, Long ideaId);
}
