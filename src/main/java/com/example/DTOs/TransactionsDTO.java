package com.example.DTOs;

import java.time.LocalDate; 

import jakarta.validation.constraints.*;
import com.example.utils.*;
public class TransactionsDTO {
	private Long id;
	@NotBlank(message = "Category is required", groups = {onCreate.class})
	 @Pattern(regexp = "^\\S.*", message = "Category must not start with a space",groups= {onUpdate.class,onCreate.class})
    private String category;

    @NotNull(message = "Amount is required", groups = {onCreate.class})
    @Positive(message = "Amount must be greater than 0", groups = {onUpdate.class,onCreate.class})
    private Double amount;

    @NotBlank(message = "Type is required", groups = {onCreate.class})
    @Pattern(regexp = "INCOME|EXPENSE", message = "Type must be either INCOME or EXPENSE", groups = {onUpdate.class,onCreate.class})
    private String type;

    @NotNull(message = "Date is required", groups = {onCreate.class})
    @PastOrPresent(message="Date should be past or present" , groups= {onCreate.class,onUpdate.class})
    private LocalDate date;

    private String description;
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
