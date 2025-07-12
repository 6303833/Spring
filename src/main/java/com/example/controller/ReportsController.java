package com.example.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.DTOs.CategoryAllAmountsDTO;
import com.example.DTOs.CategoryAmountDTO;
import com.example.DTOs.MonthAmountsDTO;
import com.example.DTOs.MonthlyTrendDTO;
import com.example.DTOs.WeeklyTrendDTO;
import com.example.service.ReportService;
@RestController
@CrossOrigin(origins="http://localhost:4200")
@RequestMapping("api/finance/reports")
public class ReportsController {
	private final ReportService reportService;
	
	public ReportsController(ReportService reportService) {
		super();
		this.reportService = reportService;
	}
	@GetMapping("/monthAmount")
    public ResponseEntity<MonthAmountsDTO> getMonthAmounts(
            @RequestParam int year,
            @RequestParam int month) {
        return ResponseEntity.ok(reportService.getMonthAmounts(year, month));
    }
    @GetMapping("/categoryAmounts")
    public ResponseEntity<List<CategoryAmountDTO>> getCategoryAmount(@RequestParam int year,
            @RequestParam int month,@RequestParam String type){
    	return ResponseEntity.ok(reportService.getCategoryAmount(year, month,type));
    }
    @GetMapping("/categoryAllAmounts")
    public ResponseEntity<List<CategoryAllAmountsDTO>> getCategoryAllAmounts(@RequestParam int year,
            @RequestParam int month ){
    	return ResponseEntity.ok(reportService.getCategoryAllAmounts(year, month ));
    }
    
    
    
    @GetMapping("/weekly-trend")
    public ResponseEntity<List<WeeklyTrendDTO>> getWeeklyTrend(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            @RequestParam(required =false) String type) {

        return ResponseEntity.ok(reportService.getWeeklyTrend(year, month, type));
    }
    @GetMapping("/monthly-trend")
    public ResponseEntity<List<MonthlyTrendDTO>> getMonthlyTrend(
            @RequestParam(required=false) String type,
            @RequestParam(required = false) Integer year) {
        return ResponseEntity.ok(reportService.getMonthlyTrend(year, type));
    }
}
