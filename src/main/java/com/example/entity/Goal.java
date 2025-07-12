package com.example.entity;

import java.time.LocalDate;

import com.example.enums.GoalStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Goal {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
    private Double targetAmount;
    private Double currentBalance;
    private LocalDate startDate; 
    private LocalDate deadlineDate;
    @Enumerated(EnumType.STRING)
    private GoalStatus status;
    public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getTargetAmount() {
		return this.targetAmount;
	}
	public void setTargetAmount(Double targetAmount) {
		this.targetAmount = targetAmount;
	}
	public Double getCurrentBalance() {
		return this.currentBalance;
	}
	public void setCurrentBalance(Double currentBalance) {
		this.currentBalance = currentBalance;
	}
	public LocalDate getStartDate() {
		return this.startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getDeadlineDate() {
		return this.deadlineDate;
	}
	public void setDeadlineDate(LocalDate deadlineDate) {
		this.deadlineDate = deadlineDate;
	}
	public GoalStatus getStatus() {
		return this.status;
	}
	public void setStatus(GoalStatus status) {
		this.status = status;
	} 
	public Goal() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Goal(Long id, String name, Double targetAmount, Double currentBalance, LocalDate startDate,
			LocalDate deadlineDate, GoalStatus status) {
		super();
		this.id = id;
		this.name = name;
		this.targetAmount = targetAmount;
		this.currentBalance = currentBalance;
		this.startDate = startDate;
		this.deadlineDate = deadlineDate;
		this.status = status;
	}
	
	
}
