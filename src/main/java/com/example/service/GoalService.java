package com.example.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.DTOs.GoalDTO;
import com.example.entity.Goal;
import com.example.entity.Transactions;
import com.example.enums.GoalStatus;
import com.example.exceptions.ResourceNotFoundException;
import com.example.mapper.GoalMapper;
import com.example.mapper.TransactionsMapper;
import com.example.repository.GoalRepo;
import com.example.repository.TransactionsRepo;
@Service
public class GoalService {
	private final GoalRepo goalRepo;
	private final TransactionsRepo transRepo;
	private final TransactionsService transService;
	private final TransactionsMapper transMapper;
	private final GoalMapper goalMapper;
	public GoalService(GoalRepo goalRepo, TransactionsRepo transRepo, TransactionsService transService,
			TransactionsMapper transMapper, GoalMapper goalMapper) {
		super();
		this.goalRepo = goalRepo;
		this.transRepo = transRepo;
		this.transService = transService;
		this.transMapper = transMapper;
		this.goalMapper = goalMapper;
	}
	public GoalDTO getGoal(Long id) {
		Goal goal = goalRepo.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Goal not found"));
		
		return goalMapper.toDto(goal);
	}
	public List<GoalDTO> getAllGoals() {
		// TODO Auto-generated method stub
		List<Goal> li=goalRepo.findAll();
		
		return goalMapper.toDtoList(li);
	}
	@SuppressWarnings("null")
	public GoalDTO addGoal(GoalDTO dto) {
		Goal goal = goalMapper.toEntity(dto); 
        goal.setId(null); 
        Goal savedGoal = goalRepo.save(goal); 
        return goalMapper.toDto(savedGoal);
    }
	
	
	public GoalDTO updateGoal(Long id, GoalDTO dto) {
        Goal goal = goalMapper.toEntity(this.getGoal(id));
        Double prevBalance = goal.getCurrentBalance();

        if (dto.getName() != null) goal.setName(dto.getName()); 
        if (dto.getTargetAmount() != null) goal.setTargetAmount(dto.getTargetAmount());
        if (dto.getDeadlineDate() != null) goal.setDeadlineDate(dto.getDeadlineDate());

        if (dto.getCurrentBalance() > prevBalance) {
            Double difference = dto.getCurrentBalance() - prevBalance;
            goal.setCurrentBalance(dto.getCurrentBalance());

            // Save EXPENSE transaction since amount was used for the goal
            Transactions t = new Transactions();
            t.setAmount(difference);
            t.setCategory("Goal");
            t.setDate(LocalDate.now());
            t.setDescription(goal.getName());
            t.setType("EXPENSE");
            transRepo.save(t);
        }

        // Update status
        if (goal.getCurrentBalance() >= goal.getTargetAmount()) {
            goal.setStatus(GoalStatus.ACHIEVED);
        } else if (LocalDate.now().isAfter(goal.getDeadlineDate())) {
            goal.setStatus(GoalStatus.INACTIVE);
        }

        return goalMapper.toDto(goalRepo.save(goal));
    }
	
	
	 public void deleteGoal(Long id) {
	        Goal goal = goalRepo.findById(id)
	            .orElseThrow(() -> new RuntimeException("Goal not found"));

	        boolean addRollbackIncome = false;

	        if (goal.getStatus() == GoalStatus.ACTIVE) {
	            addRollbackIncome = true;
	        } else if (goal.getStatus() == GoalStatus.INACTIVE &&
	                   goal.getCurrentBalance() < goal.getTargetAmount()) {
	            addRollbackIncome = true;
	        }

	        if (addRollbackIncome && goal.getCurrentBalance() > 0) {
	            Transactions t = new Transactions();
	            t.setAmount(goal.getCurrentBalance());
	            t.setCategory("Goal");
	            t.setDate(LocalDate.now());
	            t.setDescription(goal.getName());
	            t.setType("INCOME");
	            transRepo.save(t);
	        }

	        goalRepo.deleteById(id);
	    }

	

}
