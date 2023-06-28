package com.vmoscalciuc.budget.controller;


import com.vmoscalciuc.budget.model.User;
import com.vmoscalciuc.budget.model.dto.UserDto;
import com.vmoscalciuc.budget.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final UserService userService;

        @GetMapping("/mainPage")
        public String getMainPage(Model model) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Optional<User> user = userService.findByEmail(authentication.getName());
            model.addAttribute("user",user);
            return "mainPage";
        }
}