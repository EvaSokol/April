package com.april.testproject.repository;

import com.april.testproject.entity.Idea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import java.util.List;

//@Component
public interface IdeaRepository extends JpaRepository<Idea, Long>{

    @Query(value = "SELECT i FROM Idea i WHERE i.userId = ?1")
    List<Idea> findByUserId(String userId);

}