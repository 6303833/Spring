package com.example.controller;

import java.time.LocalDate;
import java.util.List; 
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.utils.*;

import jakarta.validation.Valid;

import com.example.DTOs.*; 
import com.example.service.ReportService;
import com.example.service.TransactionsService;
 
@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/api/finance")
public class FinanceController {
	private final TransactionsService transactionService;
	private final ReportService reportService;
    public FinanceController(TransactionsService transactionService, ReportService reportService) {
		super();
		this.transactionService = transactionService;
		this.reportService = reportService;
	}
	@PostMapping("/transactions")
    public ResponseEntity<TransactionsDTO> createTransaction(@RequestBody @Validated( onCreate.class) TransactionsDTO dto) {
        return ResponseEntity.ok(transactionService.createTransaction(dto));
    }
    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionsDTO>> getTransactions(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Double minAmount,
            @RequestParam(required = false) Double maxAmount) {

        return ResponseEntity.ok(transactionService.getFilteredTransactions(
            category,type, startDate, endDate, minAmount, maxAmount));
    }
    @GetMapping("/transactions/{id}")
    public ResponseEntity<TransactionsDTO> getTransactionById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    @PatchMapping("/transactions/{id}")
    public ResponseEntity<TransactionsDTO> updateTransaction(@PathVariable Long id,
                                                             @RequestBody @Validated( onUpdate.class) TransactionsDTO dto) {
        return ResponseEntity.ok(transactionService.updateTransaction(id, dto));
    }

    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<Boolean> deleteTransaction(@PathVariable Long id) {
    	return ResponseEntity.ok(transactionService.deleteTransaction(id)); 
    }
    
    
    
    
    @GetMapping("/balance")
    public ResponseEntity<BalanceSummaryDTO> getTotalBalance() {
        return ResponseEntity.ok(transactionService.getTotalIncomeAndExpense());
    }
    
    
    
    

    
    
    
    
}

