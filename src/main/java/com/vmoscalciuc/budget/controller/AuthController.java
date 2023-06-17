package com.vmoscalciuc.budget.controller;

import com.vmoscalciuc.budget.model.User;
import com.vmoscalciuc.budget.model.dto.UserDto;
import com.vmoscalciuc.budget.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class AuthController {

    private final UserService userService;


    @GetMapping("index")
    public String home(){
        return "index";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // handler method to handle user registration request
    @GetMapping("register")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    // handler method to handle register user form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto user,
                               BindingResult result,
                               Model model){

        System.out.println("hello");
        System.out.println("hello");
        Optional<User> existing = userService.findByEmail(user.getEmail());
        if (existing.isPresent()) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/register?success";
    }

//    @GetMapping("/users")
//    public String listRegisteredUsers(Model model){
//        List<UserDto> users = userService.findAllUsers();
//        model.addAttribute("users", users);
//        return "users";
//    }
}
