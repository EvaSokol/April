package com.april.testproject.repository;

import com.april.testproject.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Eva Sokolyanskaya on 19/06/2018.
 */
public interface LikeRepository extends JpaRepository<Like, Long> {
}
