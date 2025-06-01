package com.blogapplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapplication.entity.UserComments;

public interface UserCommentRepository extends JpaRepository<UserComments, Integer>{

}
