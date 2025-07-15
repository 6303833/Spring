package com.example.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.DTOs.GoalDTO;
import com.example.entity.Goal;
import com.example.entity.Transactions;
import com.example.enums.GoalStatus;
import com.example.exceptions.ResourceNotFoundException;
import com.example.mapper.GoalMapper;
import com.example.mapper.TransactionsMapper;
import com.example.repository.GoalRepo;
import com.example.repository.TransactionsRepo;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
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
		if(dto.getCurrentBalance()>0) {
			this.goalToTransaction(dto.getName(), dto.getCurrentBalance(), "EXPENSE", "ADDING");
		}
		Goal goal = goalMapper.toEntity(dto); 
        goal.setId(null); 
        Goal savedGoal = goalRepo.save(goal); 
        return goalMapper.toDto(savedGoal);
    }
	
	public void goalToTransaction(String goalName,Double amount,String type,String message) {
		Transactions t = new Transactions();
        t.setAmount(amount);
        t.setCategory("Goal");
        t.setDate(LocalDate.now());
        t.setDescription(goalName+" : "+message);
        t.setType(type);
        transRepo.save(t);
	}
	@Transactional
	public void updateGoalBalance(Long id,Double newbal) {
		GoalDTO dto=this.getGoal(id);
		Double oldbal=dto.getCurrentBalance();
		String message=""; 
		if(newbal>oldbal) {
			message="Balance update from "+oldbal +" To "+newbal;
			this.goalToTransaction(dto.getName(), newbal-oldbal, "EXPENSE", message);
			dto.setCurrentBalance(newbal);
			if(newbal>dto.getTargetAmount()) {
				dto.setStatus(GoalStatus.ACHIEVED);
			}
			this.goalRepo.save(goalMapper.toEntity(dto));
		}
	}
	public GoalDTO updateGoalNotBalance(Long id, GoalDTO dto) {
        Goal goal = goalMapper.toEntity(this.getGoal(id));
      
//        if (dto.getName() != null) goal.setName(dto.getName()); 
        if (dto.getTargetAmount() != null) goal.setTargetAmount(dto.getTargetAmount());
        if (dto.getDeadlineDate() != null) goal.setDeadlineDate(dto.getDeadlineDate());

        // Update status
        if (goal.getCurrentBalance() >= goal.getTargetAmount()) {
            goal.setStatus(GoalStatus.ACHIEVED);
        }
        else {
        	goal.setStatus(GoalStatus.ACTIVE);
        }
        return goalMapper.toDto(goalRepo.save(goal));
    }
	
	@Transactional
	 public void deleteGoal(Long id) {
	        Goal goal = goalRepo.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Goal not found"));
	        LocalDate date=LocalDate.now();
	        if(!date.isAfter(goal.getDeadlineDate())) {//current <= deadline
	        	List<Long> ids=transRepo.idsOfGoalStartingWith(goal.getName());
	        	this.transRepo.deleteByIds(ids);
	        	goalRepo.deleteById(id);        	
	        }
	        
	    }

	

}
