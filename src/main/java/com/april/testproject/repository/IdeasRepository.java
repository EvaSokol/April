package com.april.testproject.repository;

import com.april.testproject.entity.Idea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IdeasRepository extends JpaRepository<Idea, Long> {



    @Query(value = "SELECT * FROM ideas",
            nativeQuery = true
    )
    List<Idea> findBySearchTermNative(@Param("getAllIdeas") String getAllIdeas);
}
