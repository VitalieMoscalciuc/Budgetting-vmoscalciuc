package com.vmoscalciuc.budget.controller;


import com.vmoscalciuc.budget.model.User;
import com.vmoscalciuc.budget.model.dto.ExpenseDto;
import com.vmoscalciuc.budget.service.ExpenseService;
import com.vmoscalciuc.budget.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.ParseException;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class ExpenseController {

    private final UserService userService;

    private final ExpenseService expenseService;

    @GetMapping("/expensePage")
    public String getExpensePage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userService.findByEmail(authentication.getName());
        model.addAttribute("user",user);
        model.addAttribute("expenseList",expenseService.findAll(user.get().getId()));
        return "expensePage";
    }

    @PostMapping("/expense/save")
    public String saveExpense(@Valid @ModelAttribute("expense") ExpenseDto expenseDto,
                              BindingResult result,
                              Model model) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userService.findByEmail(authentication.getName());
        expenseDto.setUserId(user.get().getId());
        System.out.println("updating user Balance");
        if (result.hasErrors()) {
            System.out.println("expense save error");
            return "saveExpense";
        }
        expenseService.saveExpense(expenseDto);
        if(user.get().getBalance()>expenseDto.getAmount()){
            userService.updateUserBalance(user.get().getId(), expenseDto.getAmount());
        }
        return "redirect:/expensePage";
    }

    @GetMapping("/saveExpense")
    public String saveExpense(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userService.findByEmail(authentication.getName());
        model.addAttribute("user",user);
        ExpenseDto expense = new ExpenseDto();
        model.addAttribute("expense",expense);
        return "saveExpense";
    }


    @GetMapping("/updateExpense/{expenseId}")
    public String getUpdateExpensePage(Model model, @PathVariable(value = "expenseId") Long expenseId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userService.findByEmail(authentication.getName());
        model.addAttribute("user",user);
        ExpenseDto expense = expenseService.findById(expenseId);
        expense.setId(expenseId);
        model.addAttribute("expense",expense);
        model.addAttribute("oldAmount",expense.getAmount());
        return "updateExpense";
    }

    @PostMapping("/expense/update")
    public String updateExpense(@Valid @ModelAttribute("expense") ExpenseDto newexpenseDto,
                              BindingResult result,
                              Model model) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userService.findByEmail(authentication.getName());
        System.out.println("hello expenseId" +newexpenseDto.getId());
        System.out.println(user.get().getEmail());
        System.out.println("updating user Balance");
        System.out.println("NewExpenseDtoId="+newexpenseDto.getId());
        Double newAmount = (newexpenseDto.getAmount());
        System.out.println("new amount"+ newexpenseDto.getAmount());
        if (result.hasErrors()) {
            System.out.println("expense save error");
            return "updateExpense";
        }
        expenseService.updateExpense(newexpenseDto,newexpenseDto.getId());
        return "redirect:/expensePage";
    }





    @PostMapping("/delete/{expenseId}")
    public String deleteUser(@PathVariable Long expenseId) {
            expenseService.deleteExpense(expenseId);
        return "redirect:/expensePage";
    }
}
