package com.april.testproject.repository;

import com.april.testproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

//@Component
public interface UserRepository extends JpaRepository<User, Long> {

}
