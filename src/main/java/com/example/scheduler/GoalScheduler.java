package com.example.scheduler;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Goal;
import com.example.enums.GoalStatus;
import com.example.repository.GoalRepo;
import com.example.service.GoalService;

@Component
public class GoalScheduler {
	@Autowired
	private GoalRepo goalRepo;
	@Autowired
	private GoalService goalService;
	@Transactional
    @Scheduled(cron = "0 0 0 * * *")
	public void updateStatus() {
		List<Goal> goals=goalRepo.findByDeadlineDateBeforeAndStatusIn(LocalDate.now(), List.of(GoalStatus.ACTIVE,GoalStatus.ACHIEVED));
		for(Goal goal:goals) {
			if(GoalStatus.ACTIVE==goal.getStatus()) {
				goal.setStatus(GoalStatus.USENO);
				goalService.goalToTransaction(goal.getName(), goal.getCurrentBalance(), "INCOME", "status from ACTIVE to USENO");
			}
			else if(GoalStatus.ACHIEVED==goal.getStatus()) {
				goal.setStatus(GoalStatus.USETAR);
				goalService.goalToTransaction(goal.getName(), goal.getTargetAmount(), "INCOME", "status from ACHIEVED to USETAR");
			}
		}
		goalRepo.saveAll(goals);
	}
}
