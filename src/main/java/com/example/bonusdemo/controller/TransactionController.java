package com.example.bonusdemo.controller;

import com.example.bonusdemo.db.Transaction;
import com.example.bonusdemo.db.TransactionRepository;
import com.example.bonusdemo.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(path = "/transaction/list")
    public List<TransactionDto> list() {
        log.debug("List transactions");
        List<Transaction> transactions = transactionService.findAll();
        log.debug("Transactions: {}", transactions);

        return transactions.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @PostMapping(path = "/transaction")
    @ResponseStatus(HttpStatus.OK)
    public void save(@Valid @RequestBody TransactionDto transactionDto) {
        log.debug("Save transaction: {}", transactionDto);
        transactionService.save(convertToEntity(transactionDto));
    }

    private TransactionDto convertToDto(Transaction transaction) {
        return modelMapper.map(transaction, TransactionDto.class);
    }

    private Transaction convertToEntity(TransactionDto transactionDto) {
        return modelMapper.map(transactionDto, Transaction.class);
    }

}
