package com.steverado9.Banking.Management.System.controller;

import com.steverado9.Banking.Management.System.entity.Account;
import com.steverado9.Banking.Management.System.entity.User;
import com.steverado9.Banking.Management.System.service.AccountService;
import com.steverado9.Banking.Management.System.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.webauthn.api.PublicKeyCose;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class UserController {
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private AccountService accountService;

    public UserController(UserService userService, PasswordEncoder passwordEncoder, AccountService accountService) {
        super();
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.accountService = accountService;
    }

    @GetMapping("/create_user")
    public String createUserForm(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if(loggedInUser == null) {
            return "redirect:/sign_in";
        }

        User user = new User();
        model.addAttribute("user", user);
        return "create_user";
    }

    @PostMapping("/create_user")
    public String saveUser(@ModelAttribute("user") User user, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            User loggedInUser = (User) session.getAttribute("loggedInUser");
            if(loggedInUser == null) {
                return "redirect:/sign_in";
            }
            user.setPassword(passwordEncoder.encode(user.getPassword())); //hash password
            userService.saveUser(user);
            redirectAttributes.addFlashAttribute("successMessage", "user created successfully!, please signin");

            return "redirect:/sign_in";
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "User already exists!");
            return "redirect:/create_user";
        }
    }

    @GetMapping("/sign_in")
    public String signInForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "sign_in";
    }

    @PostMapping("/sign_in")
    public String getUser(@ModelAttribute("user") User user, HttpSession session, RedirectAttributes redirectAttributes) {
        Optional<User> existingUser = userService.getUserByUsername(user.getUsername());

        if(existingUser.isEmpty()) {
            System.out.println("user does not exist");
            redirectAttributes.addFlashAttribute("errorMessage", "invalid username or password");
            return "redirect:/sign_in";
        }

        User foundUser = existingUser.get();

        String existingPassword = existingUser.get().getPassword();
        if (!passwordEncoder.matches(user.getPassword(), existingPassword)) {
            System.out.println("Incorrect password");
            redirectAttributes.addFlashAttribute("errorMessage", "invalid username or password");
            return "redirect:/sign_in";
        }
        session.setAttribute("loggedInUser", foundUser);

        if ("Admin".equalsIgnoreCase(foundUser.getRole())) {
            return "redirect:/accounts";
        }

        Account account = accountService.getAccountByUserId(existingUser.get().getId());
        if (account == null) {
            return "redirect:/create_account";
        }

        return "redirect:/dashboard/" + account.getId();
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/sign_in";
    }

    @GetMapping("/")
    public String home() {
        System.out.println("Home");
        return "home";
    }
}
