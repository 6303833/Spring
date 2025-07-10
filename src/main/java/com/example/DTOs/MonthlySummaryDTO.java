package com.example.DTOs;

public class MonthlySummaryDTO {

    private Double totalIncome;
    private Double totalExpenses;
    private Double netSavings;
	public Double getTotalIncome() {
		return totalIncome;
	}	
	public void setTotalIncome(Double totalIncome) {
		this.totalIncome = totalIncome;
	}
	public Double getTotalExpenses() {
		return totalExpenses;
	}
	public void setTotalExpenses(Double totalExpenses) {
		this.totalExpenses = totalExpenses;
	}
	public Double getNetSavings() {
		return netSavings;
	}
	public void setNetSavings(Double netSavings) {
		this.netSavings = netSavings;
	}
	public MonthlySummaryDTO(Double totalIncome, Double totalExpenses, Double netSavings) {
		super();
		this.totalIncome = totalIncome;
		this.totalExpenses = totalExpenses;
		this.netSavings = netSavings;
	}
	public MonthlySummaryDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
}
