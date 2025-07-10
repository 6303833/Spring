package com.example.mapper;


import java.util.List;

import org.mapstruct.Mapper;

import com.example.DTOs.TransactionsDTO;
import com.example.entity.Transactions;

@Mapper(componentModel = "spring")
public interface TransactionsMapper {
    TransactionsDTO toDto(Transactions transaction);
    Transactions toEntity(TransactionsDTO dto);
    List<TransactionsDTO> toDtoList(List<Transactions> txs);
}
