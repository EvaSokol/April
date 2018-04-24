package com.april.testproject.repository;

import com.april.testproject.entity.Idea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface IdeasRepository extends JpaRepository<Idea, Long> {
}
