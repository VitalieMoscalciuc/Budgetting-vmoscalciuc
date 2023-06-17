package com.vmoscalciuc.budget.controller;

import com.vmoscalciuc.budget.model.User;
import com.vmoscalciuc.budget.model.dto.IncomeDto;
import com.vmoscalciuc.budget.service.IncomeService;
import com.vmoscalciuc.budget.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.ParseException;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class IncomeController {

    private final UserService userService;

    private final IncomeService incomeService;

    @GetMapping("/incomePage")
    public String getIncomePage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userService.findByEmail(authentication.getName());
        model.addAttribute("user",user);
        model.addAttribute("incomeList",incomeService.findAll());
        return "incomePage";
    }


    @GetMapping("/updateIncome/{incomeId}")
    public String getUpdateIncomePage(Model model, @PathVariable(value = "incomeId") Long incomeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userService.findByEmail(authentication.getName());
        model.addAttribute("user",user);
        IncomeDto income = incomeService.findById(incomeId);
        income.setId(incomeId);
        model.addAttribute("income",income);
        model.addAttribute("oldAmount",income.getAmount());
        return "updateIncome";
    }

    @PostMapping("/income/update")
    public String updateIncome(@Valid @ModelAttribute("income") IncomeDto newincomeDto,
                                BindingResult result,
                                Model model) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userService.findByEmail(authentication.getName());
        System.out.println("hello incomeId" +newincomeDto.getId());
        System.out.println(user.get().getEmail());
        System.out.println("updating user Balance");
        System.out.println("NewIncomeDtoId="+newincomeDto.getId());
        Double newAmount = (newincomeDto.getAmount());
        System.out.println("new amount"+ newincomeDto.getAmount());
        if (result.hasErrors()) {
            System.out.println("income save error");
            return "updateIncome";
        }
        incomeService.updateIncome(newincomeDto,newincomeDto.getId());
        return "redirect:/incomePage";
    }


    @GetMapping("/saveIncome")
    public String saveIncome(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userService.findByEmail(authentication.getName());
        model.addAttribute("user",user);
        IncomeDto income = new IncomeDto();
        model.addAttribute("income",income);
        return "saveIncome";
    }

    @PostMapping("/income/save")
    public String saveIncome(@Valid @ModelAttribute("income") IncomeDto incomeDto,
                              BindingResult result,
                              Model model) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userService.findByEmail(authentication.getName());
        System.out.println("hello income");
        incomeDto.setUserId(user.get().getId());
        System.out.println(incomeDto.getUserId());
        System.out.println(user.get().getEmail());
        System.out.println("updating user Balance");

        if (result.hasErrors()) {
            System.out.println("income save error");
            return "saveIncome";
        }
        incomeService.saveIncome(incomeDto);
        if(user.get().getBalance()>incomeDto.getAmount()){
            userService.addToUserBalance(user.get().getId(), incomeDto.getAmount());
        }
        return "redirect:/incomePage";
    }

    @PostMapping("income/delete/{incomeId}")
    public String deleteUser(@PathVariable Long incomeId) {
        incomeService.deleteIncome(incomeId);
        return "redirect:/incomePage";
    }
}
