package com.steverado9.Banking.Management.System.controller;

import com.steverado9.Banking.Management.System.entity.User;
import com.steverado9.Banking.Management.System.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    public UserController(UserService userService) {
        super();
        this.userService = userService;
    }

    @GetMapping("/create_user")
    public String createUserForm(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUSer");

        if(loggedInUser == null) {
            return "redirect:/sign_in";
        }

        User user = new User();
        model.addAttribute("user", user);
        return "create_user";
    }

    @PostMapping("/create_user")
    public String saveUSer(@ModelAttribute("user") User user, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            User loggedInUser = (User) session.getAttribute("loggedInUser");

            if(loggedInUser == null) {
                return "redirect:/sign_in";
            }

            user.setPassword(passwordEncoder.encode(user.getPassword())); //hash password
            userService.saveUser(user);
            redirectAttributes.addFlashAttribute("successMessage", "Member created sucessfully!, please signin");

            return "redirect:/sign_in";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("errorMessage", "Email already exists!");
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
        Optional existingUser = userService.getUserByUsername(user.getUsername());

        if(existingUser == null) {
            System.out.println("user does not exist");
            redirectAttributes.addFlashAttribute("errorMessage", "invalid email and password");
            return "redirect:/sign_in";
        }

//        String existingPassword = existingUser.getClass();
        if (!user.getPassword().equalsIgnoreCase(existingPassword)) {
            System.out.println("Incorrect password");
            redirectAttributes.addFlashAttribute("errorMessage", "invalid email and password");
            return "redirect:/sign_in";
        }
        session.setAttribute("loggedInUser", existingUser);

        return "redirect:/accounts";
    }
}
