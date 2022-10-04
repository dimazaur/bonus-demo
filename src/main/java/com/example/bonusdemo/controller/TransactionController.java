package com.example.bonusdemo.controller;

import com.example.bonusdemo.db.Transaction;
import com.example.bonusdemo.db.TransactionRepository;
import com.example.bonusdemo.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(path = "/transaction/list/{userId}")
    public List<TransactionDto> list(@PathVariable String userId) {
        log.debug("List transactions for userId: {}", userId);
        List<Transaction> transactions = transactionService.findByUserId(userId);
        log.debug("Transactions: {}", transactions);

        return transactions.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping(path = "/transaction/date/{userId}")
    public List<TransactionDto> list(@PathVariable String userId,
                                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime  dateFrom,
                                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime dateTo) {

        log.debug("List transactions for userId: {}, dataFrom: {}, dateTo: {}", userId, dateFrom, dateTo);
        List<Transaction> transactions = transactionService.findByUserIdDate(userId, dateFrom, dateTo);
        log.debug("Transactions: {}", transactions);

        return transactions.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @PostMapping(path = "/transaction")
    @ResponseStatus(HttpStatus.OK)
    public void save(@Valid @RequestBody TransactionDto transactionDto) {
        log.debug("Save transaction: {}", transactionDto);
        transactionService.save(convertToEntity(transactionDto));
    }

    @PostMapping(path = "/transaction/list")
    @ResponseStatus(HttpStatus.OK)
    public void save(@Valid @RequestBody List<TransactionDto> transactionDtoList) {
        log.debug("Save transaction list: {}", transactionDtoList);
        transactionService.saveAll(transactionDtoList.stream().map(this::convertToEntity).toList());
    }

    private TransactionDto convertToDto(Transaction transaction) {
        return modelMapper.map(transaction, TransactionDto.class);
    }

    private Transaction convertToEntity(TransactionDto transactionDto) {
        return modelMapper.map(transactionDto, Transaction.class);
    }

}
