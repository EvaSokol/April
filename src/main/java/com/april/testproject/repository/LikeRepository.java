package com.april.testproject.repository;

import com.april.testproject.entity.Idea;
import com.april.testproject.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * Created by Eva Sokolyanskaya on 19/06/2018.
 */
public interface LikeRepository extends JpaRepository<Like, Long> {

	@Query(value = "SELECT COUNT(l) FROM Like l WHERE l.ideaId = ?1")
	int getLikesOfIdea(Long ideaId);

	@Query(value = "SELECT new map(l.ideaId as ideaId, COUNT(l) as rate) FROM Like l GROUP BY l.ideaId ORDER BY (COUNT(l)) DESC")
	List<Map<Integer, Long>> getIdeasSortedByLikes();

	@Query(value = "SELECT DISTINCT l.ideaId FROM Like l")
	List<Long> getLikedIdeas();

	@Query(value = "SELECT l FROM Like l WHERE l.userId = ?1 AND l.ideaId = ?2")
	List<Like> findLike(Long userId, Long ideaId);
}
