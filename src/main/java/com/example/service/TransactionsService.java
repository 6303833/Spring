package com.example.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.DTOs.CategoryAllAmountDTO;
import com.example.DTOs.CategoryAmountDTO;
import com.example.DTOs.TransactionsDTO;
import com.example.entity.Transactions;
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
            .orElseThrow(() -> new RuntimeException("Transaction not found with id " + id));
        return mapper.toDto(entity);
    }

    public TransactionsDTO updateTransaction(Long id, TransactionsDTO dto) {
        Transactions existing = txRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Transaction not found with id " + id));

        existing.setAmount(dto.getAmount());
        existing.setCategory(dto.getCategory());
        existing.setDate(dto.getDate());
        existing.setDescription(dto.getDescription());
        existing.setType(dto.getType());

        return mapper.toDto(txRepo.save(existing));
    }

    public void deleteTransaction(Long id) {
        if (!txRepo.existsById(id)) {
            throw new RuntimeException("Transaction not found with id " + id);
        }
        txRepo.deleteById(id);
    }
    

     
}
