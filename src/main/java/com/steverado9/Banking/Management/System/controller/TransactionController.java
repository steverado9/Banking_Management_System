package com.steverado9.Banking.Management.System.controller;

import com.steverado9.Banking.Management.System.entity.Account;
import com.steverado9.Banking.Management.System.entity.Transaction;
import com.steverado9.Banking.Management.System.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TransactionController {
    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/deposit")
    public String depositForm(Model model) {
        Transaction transaction = new Transaction();
        model.addAttribute("transaction", transaction);
        return "deposit";
    }

    @PostMapping("/deposit")
    public String SaveDeposit(@RequestParam Long accountId, @ModelAttribute("transaction") Transaction transaction) {
            transactionService.deposit(accountId, transaction);
            return "redirect:/dashboard";
    }

    @GetMapping("/withdraw")
    public String withdrawalForm(Model model) {
        Transaction transaction = new Transaction();
        model.addAttribute("transaction", transaction);
        return "withdraw";
    }

    @PostMapping("/withdraw")
    public String saveWithdrawal(@RequestParam Long accountId, @ModelAttribute("transaction") Transaction transaction) {
        transactionService.withdraw(accountId, transaction);
        return "redirect:/dashboard";
    }

    @GetMapping("/transfer")
    public String transferForm(Model model) {
        Transaction transaction = new Transaction();
        model.addAttribute("transaction", transaction);
        return "transfer";
    }

    @PostMapping("/transfer")
    public String transfer(@RequestParam Long accountId, @ModelAttribute("transaction") Transaction transaction) {
        transactionService.transfer(accountId, transaction);
        return "redirect:/dashboard";
    }
}
