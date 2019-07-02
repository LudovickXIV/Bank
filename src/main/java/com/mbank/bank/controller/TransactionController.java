package com.mbank.bank.controller;

import com.mbank.bank.FileHelper;
import com.mbank.bank.TransactionPdf;
import com.mbank.bank.dto.TransactionDto;
import com.mbank.bank.service.impl.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/send_money")
public class TransactionController {

    @Autowired
    private TransactionServiceImpl transactionService;

    @Transactional
    @PostMapping
    public ResponseEntity<String> sendMoneyTo(@RequestBody TransactionDto target) {
        transactionService.sendTo(target);
        return ResponseEntity.ok("");
    }
}
