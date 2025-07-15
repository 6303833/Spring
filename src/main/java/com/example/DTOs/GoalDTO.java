package com.example.DTOs; 

import java.time.LocalDate;

import com.example.enums.GoalStatus;
import com.example.utils.onCreate;
import com.example.utils.onUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;  
 
public class GoalDTO { 
	private Long id;
	
	@NotBlank(message = "Goal Name is required", groups = {onCreate.class})
	 @Pattern(regexp = "^\\S.*", message = "Goal Name must not start with a space",groups= {onCreate.class})
	private String name;
	
	@NotNull(message = "Target Amount should not be empty",groups = {onCreate.class})
	@Min(value=1,message = "Goal Target amount should be greater than 0",groups = {onCreate.class,onUpdate.class})
    private Double targetAmount;
	
	@NotNull(message = "Goal Current balance should not be empty",groups = {onCreate.class})
	@Min(value=0, message = "Goal Current balance should not be negative",groups = {onCreate.class,onUpdate.class})
    private Double currentBalance;
	
	@NotNull(message = "Date cannot be null",groups = {onCreate.class})
    @PastOrPresent(message = "Date must be in the past or present",groups = {onCreate.class}) 
    private LocalDate startDate;
	
	@NotNull(message = "Deadline Date cannot be null",groups = {onCreate.class})
    @FutureOrPresent(message = "Deadline Date should be today or later",groups = {onCreate.class,onUpdate.class})
    private LocalDate deadlineDate;
	
	@NotNull(message = "Date cannot be null",groups = {onCreate.class})
    @Enumerated(EnumType.STRING)
    private GoalStatus status;
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
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
		return currentBalance;
	}
	public void setCurrentBalance(Double currentBalance) {
		this.currentBalance = currentBalance;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getDeadlineDate() {
		return deadlineDate;
	}
	public void setDeadlineDate(LocalDate deadlineDate) {
		this.deadlineDate = deadlineDate;
	}
	public GoalStatus getStatus() {
		return status;
	}
	public void setStatus(GoalStatus status) {
		this.status = status;
	}
	
	public GoalDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GoalDTO(Long id, String name, Double targetAmount, Double currentBalance, LocalDate startDate,
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

