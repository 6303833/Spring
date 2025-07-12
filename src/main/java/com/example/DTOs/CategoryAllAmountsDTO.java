package com.example.DTOs;

public class CategoryAllAmountsDTO {
	private String category;
	private Double expense;
	private Double income;
	private Double totalAmount;
	public CategoryAllAmountsDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CategoryAllAmountsDTO(String category, Double expense, Double income, Double totalAmount) {
		super();
		this.category = category;
		this.expense = expense;
		this.income = income;
		this.totalAmount = totalAmount;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Double getExpense() {
		return expense;
	}
	public void setExpense(Double expense) {
		this.expense = expense;
	}
	public Double getIncome() {
		return income;
	}
	public void setIncome(Double income) {
		this.income = income;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
}
