package com.april.testproject.repository;

import com.april.testproject.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

/**
 * Created by Eva Sokolyanskaya on 17/06/2018.
 */
public interface TagRepository extends JpaRepository<Tag, Long> {
	@Query(value = "SELECT t FROM Tag t WHERE t.name = ?1")
	List<Tag> findByTagName(String tagName);


}
