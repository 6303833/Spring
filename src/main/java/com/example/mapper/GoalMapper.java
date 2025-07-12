package com.example.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.DTOs.GoalDTO;
import com.example.entity.Goal;

@Mapper(componentModel = "spring")
public interface GoalMapper {
	
	GoalDTO toDto(Goal goal);
	List<GoalDTO> toDtoList(List<Goal> goal);
	Goal toEntity(GoalDTO goal);
}
