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
//@RequestMapping("/")
public class MainController {

    private final UserService userService;

//    @GetMapping("/mainPage")
//    public String mainPage() {
//        return "mainPage";
//    }
        @GetMapping("/mainPage")
        public String getMainPage(Model model) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Optional<User> user = userService.findByEmail(authentication.getName());
            model.addAttribute("user",user);
            return "mainPage";
        }




//    @RequestMapping("/mainPage")
//    public String index(Model model) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (!(auth instanceof AnonymousAuthenticationToken))
//            return "mainPage";
//
//        return "login";
//    }
}

//    @GetMapping("/mainPage")
//    public ResponseEntity<String> getMainPage(Authentication authentication) {
//        // Check if the user has the required role
//        if (authentication != null && authentication.getAuthorities().stream()
//                .anyMatch(role -> role.getAuthority().equals("USER"))) {
//            // User has the required role, allow access
//            return ResponseEntity.ok("Welcome to the main page!");
//        } else {
//            // User does not have the required role, deny access
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
//        }
//    }


//    @GetMapping("/login")
//    public String loginForm() {
//        return "login";
//    }
//
//    // handler method to handle user registration request
//    @GetMapping("register")
//    public String showRegistrationForm(Model model){
//        UserDto user = new UserDto();
//        model.addAttribute("user", user);
//        return "register";
//    }

    // handler method to handle register user form submit request
//    @PostMapping("/register/save")
//    public String registration(@Valid @ModelAttribute("user") UserDto user,
//                               BindingResult result,
//                               Model model){
//
//        System.out.println("hello");
//        System.out.println("hello");
//        Optional<User> existing = userService.findByEmail(user.getEmail());
//        if (existing.isPresent()) {
//            result.rejectValue("email", null, "There is already an account registered with that email");
//        }
//        if (result.hasErrors()) {
//            model.addAttribute("user", user);
//            return "register";
//        }
//        userService.saveUser(user);
//        return "redirect:/register?success";
//    }

//    @GetMapping("/users")
//    public String listRegisteredUsers(Model model){
//        List<UserDto> users = userService.findAllUsers();
//        model.addAttribute("users", users);
//        return "users";
//    }
//}
