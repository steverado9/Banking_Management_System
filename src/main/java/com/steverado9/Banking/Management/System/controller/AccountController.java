package com.steverado9.Banking.Management.System.controller;

import com.steverado9.Banking.Management.System.entity.Account;
import com.steverado9.Banking.Management.System.entity.Transaction;
import com.steverado9.Banking.Management.System.entity.User;
import com.steverado9.Banking.Management.System.service.AccountService;
import com.steverado9.Banking.Management.System.service.TransactionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AccountController {

    private AccountService accountService;
    private TransactionService transactionService;

    public AccountController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    //go to the create account page
    @GetMapping("/create_account")
    public String createAccountForm(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/sign_in";
        }

        Account account = new Account();
        model.addAttribute("account", account);
        return "create_account";
    }

    //post an account after creation
    @PostMapping("/create_account")
    public String saveAccount(@ModelAttribute("account") Account account, Model model, HttpSession session) {
        // Get the currently logged-in user from session
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/sign_in";
        }

        // Assign the user as the owner of the new account
        account.setOwner(loggedInUser);
        //set account balance
        account.setBalance(0.00);

        Account savedAccount = accountService.saveAccount(account);
        return "redirect:/dashboard/" + savedAccount.getId();
    }

    //Edit an account
    @GetMapping("account/edit/{id}")
    public String editAccountForm(@PathVariable Long id, Model model) {
        model.addAttribute("account", accountService.getAccountById(id));
        return "edit_account";
    }

    @PutMapping("account/edit/{id}")
    public String updateArticle(@PathVariable Long id, @ModelAttribute("account") Account account, Model model) {
        Account existingAccount = accountService.getAccountById(id);
        existingAccount.setAccountType(account.getAccountType());
        existingAccount.setBalance(account.getBalance());

        accountService.updateAccount(existingAccount);
        return "redirect:/accounts";
    }

    //Delete account
    @DeleteMapping("/account/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        accountService.deleteAccountById(id);
        return "redirect:/accounts";
    }

    //Display list of accounts
    @GetMapping("/accounts")
    public String listOfAccounts(Model model, HttpSession session) {
        List<Account> accounts;
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        accounts = accountService.getAllAccounts();

        model.addAttribute("accounts", accounts);
        model.addAttribute("user", loggedInUser);
        return "accounts";
    }

    //Customer Dashboard
    @GetMapping("/dashboard/{id}")
    public String customerDashboard(@PathVariable Long id, Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/sign_in";
        }

        Account userAccount = accountService.getAccountById(id);
        List<Transaction> transactions = transactionService.getTransactionsByAccountId(id);

        model.addAttribute("user", loggedInUser);
        model.addAttribute("account", userAccount);
        model.addAttribute("transactions", transactions);
        return "dashboard";

    }
}
