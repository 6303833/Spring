package com.example.DTOs;

import java.time.LocalDate;

public class TransactionsDTO {
	private Long id;
    private String category;
    private String description;
    private Double amount;
    private LocalDate date;
    private String type;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public TransactionsDTO(Long id, String category, String description, Double amount, LocalDate date, String type) {
		super();
		this.id = id;
		this.category = category;
		this.description = description;
		this.amount = amount;
		this.date = date;
		this.type = type;
	}
	public TransactionsDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
}
