package com.steverado9.Banking.Management.System.controller;

import com.steverado9.Banking.Management.System.entity.Account;
import com.steverado9.Banking.Management.System.entity.Transaction;
import com.steverado9.Banking.Management.System.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TransactionController {
    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/account/{accountId}/deposit")
    public String depositForm(@PathVariable Long accountId, Model model) {
        Transaction transaction = new Transaction();
        model.addAttribute("transaction", transaction);
        model.addAttribute("accountId", accountId);
        return "deposit";
    }

    @PostMapping("/account/{accountId}/deposit")
    public String SaveDeposit(@PathVariable Long accountId, @ModelAttribute("transaction") Transaction transaction) {
            transactionService.deposit(accountId, transaction);
            return "redirect:/dashboard/" + accountId;
    }

    @GetMapping("/account/{accountId}/withdraw")
    public String withdrawalForm(@PathVariable Long accountId, Model model) {
        Transaction transaction = new Transaction();
        model.addAttribute("transaction", transaction);
        model.addAttribute("accountId", accountId);
        return "withdraw";
    }

    @PostMapping("/account/{accountId}/withdraw")
    public String saveWithdrawal(@PathVariable Long accountId, @ModelAttribute("transaction") Transaction transaction) {
        transactionService.withdraw(accountId, transaction);
        return "redirect:/dashboard/" + accountId;
    }

    @GetMapping("/account/{accountId}/transfer")
    public String transferForm(@PathVariable Long accountId, Model model) {
        Transaction transaction = new Transaction();
        model.addAttribute("transaction", transaction);
        model.addAttribute("accountId", accountId);
        return "transfer";
    }

    @PostMapping("/account/{accountId}/transfer")
    public String transfer(@PathVariable Long accountId, @ModelAttribute("transaction") Transaction transaction) {
        transactionService.transfer(accountId, transaction);
        return "redirect:/dashboard/" + accountId;
    }
}
