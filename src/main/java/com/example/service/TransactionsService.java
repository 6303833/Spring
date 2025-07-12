package com.example.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.DTOs.BalanceSummaryDTO; 
import com.example.DTOs.TransactionsDTO;
import com.example.entity.Transactions;
import com.example.exceptions.ResourceNotFoundException;
import com.example.mapper.TransactionsMapper;
import com.example.repository.TransactionsRepo;

@Service
public class TransactionsService {
	private final TransactionsRepo txRepo;
	private final TransactionsMapper mapper;
    public TransactionsService(TransactionsRepo txRepo, TransactionsMapper mapper) {
		super();
		this.txRepo = txRepo;
		this.mapper = mapper;
	}

	public TransactionsDTO createTransaction(TransactionsDTO dto) {
        Transactions tx = mapper.toEntity(dto);
        return mapper.toDto(txRepo.save(tx));
    }

    public  List<TransactionsDTO> getFilteredTransactions(String category,String type, LocalDate start,
                                                        LocalDate end, Double min, Double max) {
    	List<Transactions> transactions = txRepo.findAll();

        return transactions.stream()
            .filter(tx -> category == null || tx.getCategory().equalsIgnoreCase(category))
            .filter(tx -> type == null || tx.getType().equalsIgnoreCase(type))
            .filter(tx -> (start == null || !tx.getDate().isBefore(start)))
            .filter(tx -> (end == null || !tx.getDate().isAfter(end)))
            .filter(tx -> (min == null || tx.getAmount() >= min))
            .filter(tx -> (max == null || tx.getAmount() <= max))
            .map(mapper::toDto)
            .collect(Collectors.toList());
    }
    public TransactionsDTO getTransactionById(Long id) {
        Transactions entity = txRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id " + id));
        return mapper.toDto(entity);
    }

    public TransactionsDTO updateTransaction(Long id, TransactionsDTO dto) {
        Transactions existing = txRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id " + id));

        if(dto.getAmount()!=null) existing.setAmount(dto.getAmount());
        if(dto.getCategory()!=null) existing.setCategory(dto.getCategory());
        if(dto.getDate()!=null) existing.setDate(dto.getDate());
        if(dto.getDescription()!=null) existing.setDescription(dto.getDescription());
        if(dto.getType()!=null) existing.setType(dto.getType());

        return mapper.toDto(txRepo.save(existing));
    }

    public void deleteTransaction(Long id) {
        if (!txRepo.existsById(id)) {
            throw new ResourceNotFoundException("Transaction not found with id " + id);
        }
        txRepo.deleteById(id);
    }
    
    public BalanceSummaryDTO getTotalIncomeAndExpense() { 
    	    List<Object[]> result = txRepo.getTotalIncomeAndExpense(); 
    	    Object[] arr=result.get(0);
    	    double income = (arr[0] != null)  ? ((Number) arr[0]).doubleValue()  : 0.0;
    	    double expense = (arr[1] != null)  ? ((Number) arr[1]).doubleValue(): 0.0;
    	    return new BalanceSummaryDTO(expense, income, income - expense);
    }
    

     
}
