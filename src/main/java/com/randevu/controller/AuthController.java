package com.randevu.controller;

import com.randevu.model.User;
import com.randevu.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        // Validation
        if (userService.usernameExists(user.getUsername())) {
            redirectAttributes.addFlashAttribute("error", "Bu kullanıcı adı zaten kullanılıyor");
            return "redirect:/register";
        }

        if (userService.emailExists(user.getEmail())) {
            redirectAttributes.addFlashAttribute("error", "Bu e-posta adresi zaten kullanılıyor");
            return "redirect:/register";
        }

        // Save user
        userService.registerNewUser(user);
        redirectAttributes.addFlashAttribute("success", "Kayıt işlemi başarılı, lütfen giriş yapın");
        return "redirect:/login";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(auth.getName()).orElse(null);
        
        if (user != null) {
            if (user.getRole() == User.Role.PATIENT) {
                return "redirect:/patient/dashboard";
            } else if (user.getRole() == User.Role.DOCTOR) {
                return "redirect:/doctor/dashboard";
            }
        }
        
        return "redirect:/login";
    }
} 