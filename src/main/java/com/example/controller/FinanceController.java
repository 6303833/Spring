package com.example.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.DTOs.CategoryAllAmountDTO;
import com.example.DTOs.CategoryAmountDTO;
import com.example.DTOs.MonthlySummaryDTO;
import com.example.DTOs.TransactionsDTO;
import com.example.DTOs.WeeklyTrendDTO;
import com.example.entity.Transactions;
import com.example.repository.TransactionsRepo;
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
    public ResponseEntity<TransactionsDTO> createTransaction(@RequestBody TransactionsDTO dto) {
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

    @PutMapping("/transactions/{id}")
    public ResponseEntity<TransactionsDTO> updateTransaction(@PathVariable Long id,
                                                             @RequestBody TransactionsDTO dto) {
        return ResponseEntity.ok(transactionService.updateTransaction(id, dto));
    }

    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
    
    
    
    
    @GetMapping("/reports/monthly-summary")
    public ResponseEntity<MonthlySummaryDTO> getMonthlySummary(
            @RequestParam int year,
            @RequestParam int month) {
        return ResponseEntity.ok(reportService.getMonthlySummary(year, month));
    }
    @GetMapping("/reports/category-byMonth-byTransactionTypeAmount")
    public ResponseEntity<List<CategoryAmountDTO>> getCategorySummaryForType(@RequestParam int year,
            @RequestParam int month,@RequestParam String type){
    	return ResponseEntity.ok(reportService.getCategorySummaryForType(year, month,type));
    }
    @GetMapping("/reports/category-byMonth-AllAmount")
    public ResponseEntity<List<CategoryAllAmountDTO>> getCategorySummaryInDetail(@RequestParam int year,
            @RequestParam int month ){
    	return ResponseEntity.ok(reportService.getCategorySummaryInDetail(year, month ));
    }
    
    
    
    @GetMapping("/reports/weekly-trend")
    public ResponseEntity<List<WeeklyTrendDTO>> getWeeklyTrend(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            @RequestParam(required =false) String type) {

        return ResponseEntity.ok(reportService.getWeeklyTrend(year, month, type));
    }
}

