package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Goal;

public interface GoalRepo extends JpaRepository<Goal, Long>{

}
