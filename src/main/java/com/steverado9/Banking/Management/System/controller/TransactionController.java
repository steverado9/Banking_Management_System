package com.steverado9.Banking.Management.System.controller;

import com.steverado9.Banking.Management.System.entity.Transaction;
import com.steverado9.Banking.Management.System.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TransactionController {
    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/deposit")
    public String deposit(Model model) {
        Transaction
        model.addAttribute("amount", )
    }
}
