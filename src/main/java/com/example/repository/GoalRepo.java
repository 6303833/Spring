package com.example.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Goal;
import com.example.enums.GoalStatus;

public interface GoalRepo extends JpaRepository<Goal, Long>{
	List<Goal> findByDeadlineDateBeforeAndStatusIn(LocalDate date, List<GoalStatus> status);

}
