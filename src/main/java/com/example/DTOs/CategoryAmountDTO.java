package com.example.DTOs;

public class CategoryAmountDTO {
    private String category;
    private Double amount;
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public CategoryAmountDTO(String category,  Double amount ) {
		super();
		this.category = category;
		this.amount = amount; 
	} 
	public Double getAmount() {
		return amount;
	} 
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public CategoryAmountDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
}
