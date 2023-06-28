package com.vmoscalciuc.budget.controller;

import com.vmoscalciuc.budget.model.User;
import com.vmoscalciuc.budget.model.dto.AddInvestmentToGoalDto;
import com.vmoscalciuc.budget.model.dto.ShowGoalDto;
import com.vmoscalciuc.budget.service.GoalService;
import com.vmoscalciuc.budget.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Optional;
import com.vmoscalciuc.budget.model.dto.GoalDto;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;

@RequiredArgsConstructor
@Controller
public class GoalController {

    private final UserService userService;

    private final GoalService goalService;

    @GetMapping("/goalPage")
    public String getGoalPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userService.findByEmail(authentication.getName());
        model.addAttribute("user",user);
        model.addAttribute("goalList",goalService.findAll(user.get().getId()));
        return "goalPage";
    }


    @GetMapping("/updateGoal/{goalId}")
    public String getUpdateGoalPage(Model model, @PathVariable(value = "goalId") Long goalId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userService.findByEmail(authentication.getName());
        model.addAttribute("user",user);
        GoalDto goal = goalService.findById(goalId);
        goal.setId(goalId);
        model.addAttribute("goal",goal);
        model.addAttribute("oldAmount",goal.getAmount());
        return "updateGoal";
    }

    @PostMapping("/goal/update")
    public String updateGoal(@Valid @ModelAttribute("goal") GoalDto newgoalDto,
                                BindingResult result,
                                Model model) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (result.hasErrors()) {
            System.out.println("goal save error");
            return "updateGoal";
        }
        goalService.updateGoal(newgoalDto,newgoalDto.getId());
        return "redirect:/goalPage";
    }


    @GetMapping("/saveGoal")
    public String saveGoal(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userService.findByEmail(authentication.getName());
        model.addAttribute("user",user);
        GoalDto goal = new GoalDto();
        model.addAttribute("goal",goal);
        return "saveGoal";
    }


    @PostMapping("/addInvestment")
    public String addFunds(@Valid @ModelAttribute("showGoalDto") AddInvestmentToGoalDto goalDto) {
        System.out.println("ID SHOW GOAL="+ goalDto.getId());
        goalService.addFundsToGoal(goalDto.getId(), goalDto.getAmount());
        return "redirect:/moreGoalDetails/"+goalDto.getId();
    }

    @PostMapping("/goal/save")
    public String saveGoal(@Valid @ModelAttribute("goal") GoalDto goalDto,
                              BindingResult result,
                              Model model) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userService.findByEmail(authentication.getName());
        System.out.println("hello goal");
        goalDto.setUserId(user.get().getId());
        if (result.hasErrors()) {
            System.out.println("goal save error");
            return "saveGoal";
        }
        goalService.saveGoal(goalDto);
        return "redirect:/goalPage";
    }

    @PostMapping("goal/delete/{goalId}")
    public String deleteUser(@PathVariable Long goalId) {
        goalService.deleteGoal(goalId);
        return "redirect:/goalPage";
    }

    @GetMapping( "/moreGoalDetails/{goalDetailsId}")
    public String showMoreDetails(@PathVariable Long goalDetailsId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userService.findByEmail(authentication.getName());
        ShowGoalDto goalDto = goalService.showGoalDetails(goalDetailsId);
        AddInvestmentToGoalDto goalDto1 = new AddInvestmentToGoalDto();
        goalDto1.setId(goalDto.getId());
        model.addAttribute("user",user);
        model.addAttribute("goalDetails", goalDto);
        model.addAttribute("showGoalDto", goalDto1);
        return "moreGoalDetails";
    }
}

