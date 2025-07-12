package com.example.DTOs;

public class MonthlyTrendDTO {
	int month;
	double amount;
	public MonthlyTrendDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MonthlyTrendDTO(int month, double amount) {
		super();
		this.month = month;
		this.amount = amount;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
}
