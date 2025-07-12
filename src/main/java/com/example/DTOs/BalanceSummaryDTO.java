package com.example.DTOs;

public class BalanceSummaryDTO {
	double expense;
	double income;
	double balance;
	public double getExpense() {
		return expense;
	}
	public void setExpense(double expense) {
		this.expense = expense;
	}
	public double getIncome() {
		return income;
	}
	public void setIncome(double income) {
		this.income = income;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public BalanceSummaryDTO(double expense, double income, double balance) {
		super();
		this.expense = expense;
		this.income = income;
		this.balance = balance;
	}
	public BalanceSummaryDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
}
