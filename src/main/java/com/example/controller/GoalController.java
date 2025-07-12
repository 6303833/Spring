package com.example.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.DTOs.GoalDTO;
import com.example.service.GoalService;
import com.example.utils.onCreate;
import com.example.utils.onUpdate;

@RestController
@RequestMapping("/api/finance/goals")
@CrossOrigin(origins = "http://localhost:4200") 
public class GoalController {

    private final GoalService goalService;

    public GoalController(GoalService goalService) {
		super();
		this.goalService = goalService;
	}
    @GetMapping
    public ResponseEntity<List<GoalDTO>> getAllGoals(){
    	return ResponseEntity.ok(goalService.getAllGoals());
    }
    @GetMapping("/{id}")
    public ResponseEntity<GoalDTO> getGoal(@PathVariable Long id){
    	return ResponseEntity.ok(goalService.getGoal(id));
    }
	@PostMapping
    public ResponseEntity<GoalDTO> addGoal(@RequestBody @Validated(onCreate.class) GoalDTO dto) {
        return ResponseEntity.ok(goalService.addGoal(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GoalDTO> updateGoal(@PathVariable Long id, @RequestBody @Validated(onUpdate.class) GoalDTO dto) {
        return ResponseEntity.ok(goalService.updateGoal(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGoal(@PathVariable Long id) {
        goalService.deleteGoal(id);
        return ResponseEntity.noContent().build();
    }
}
