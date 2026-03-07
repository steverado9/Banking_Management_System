package com.steverado9.Banking.Management.System.controller;

import com.steverado9.Banking.Management.System.entity.Account;
import com.steverado9.Banking.Management.System.entity.Transaction;
import com.steverado9.Banking.Management.System.repository.TransactionRepository;
import com.steverado9.Banking.Management.System.service.AccountService;
import com.steverado9.Banking.Management.System.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class AnalyticsController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/dashboard/{id}/analytics")
    public String analytics(@PathVariable Long id, Model model) {

        Account userAccount = accountService.getAccountById(id);
        List<Transaction> transactions = transactionService.getTransactionsByAccountId(id);

        double received = transactionService.totalDeposits(transactions);
        double spent = transactionService.totalWithdrawals(transactions);
        System.out.println("received " + received);
        System.out.println("spent " + spent);

        model.addAttribute("received", received);
        model.addAttribute("spent", spent);
        model.addAttribute("account", userAccount);

        return "analytics";
    }
}
