package com.example.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.DTOs.*;
import com.example.repository.TransactionsRepo;

@Service
public class ReportService {
	private final TransactionsRepo txRepo;
	private final TransactionsService txService;
    public ReportService(TransactionsService txService,TransactionsRepo txrepo) {
		super();
		this.txService = txService;
		this.txRepo=txrepo;
	}

	public MonthAmountsDTO getMonthAmounts(int year, int month) {
        List<TransactionsDTO> txs = txService.getFilteredTransactions(
            null,
            null,
            LocalDate.of(year, month, 1),
            LocalDate.of(year, month, YearMonth.of(year, month).lengthOfMonth()),
            null,
            null
        );

        double income = txs.stream()
            .filter(t -> "INCOME".equalsIgnoreCase(t.getType()))
            .mapToDouble(TransactionsDTO::getAmount).sum();

        double expenses = txs.stream()
            .filter(t -> "EXPENSE".equalsIgnoreCase(t.getType()))
            .mapToDouble(TransactionsDTO::getAmount).sum();

        return new MonthAmountsDTO(income, expenses, income - expenses);
    }

	public List<CategoryAmountDTO> getCategoryAmount(int year,int month,String type){
    	return txRepo.getCategoryAmount(year, month,type);
    }
    public List<CategoryAllAmountsDTO> getCategoryAllAmounts(int year,int month ){
    	return txRepo.getCategoryAllAmounts(year, month );
    }
    
    
    public List<WeeklyTrendDTO> getWeeklyTrend(Integer year,Integer month,String type){
    	LocalDate now=LocalDate.now();
    	if(year == null) year=now.getYear();
    	if(month==null) month=now.getMonthValue();
    	if(type==null) type="EXPENSE";
    	LocalDate start=LocalDate.of(year, month, 1);
    	LocalDate end=start.withDayOfMonth(start.lengthOfMonth());
    	List<TransactionsDTO> transactions=txService.getFilteredTransactions(null, type, start, end, null, null);
    	// Group by week
        Map<Integer, Double> weekMap = transactions.stream()
            .collect(Collectors.groupingBy(
                t -> {
                    int day = t.getDate().getDayOfMonth();
                    return (day - 1) / 7 + 1;  // Week 1 to 5
                },
                Collectors.summingDouble(TransactionsDTO::getAmount)
            ));

        // Return as list of WeeklyTrendDTO (ensure all 1–5 weeks covered)
        List<WeeklyTrendDTO> result = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            result.add(new WeeklyTrendDTO(i, weekMap.getOrDefault(i, 0.0)));
        }

        return result;
    }

    public List<MonthlyTrendDTO> getMonthlyTrend(Integer year, String type) {
        int y = (year != null) ? year : LocalDate.now().getYear();
        if(type==null) type="EXPENSE";
        List<MonthlyTrendDTO> resultFromDb = txRepo.getMonthlyAmountByYearAndType(y, type);

        // Map results for fast lookup
        Map<Integer, Double> monthMap = resultFromDb.stream()
            .collect(Collectors.toMap(MonthlyTrendDTO::getMonth, MonthlyTrendDTO::getAmount));

        List<MonthlyTrendDTO> fullResult = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            fullResult.add(new MonthlyTrendDTO(i, monthMap.getOrDefault(i, 0.0)));
        }

        return fullResult;
    }

}
