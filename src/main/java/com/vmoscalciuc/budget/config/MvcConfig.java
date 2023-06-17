package com.vmoscalciuc.budget.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/mainPage").setViewName("mainPage");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/register").setViewName("register");
        registry.addViewController("/expensePage").setViewName("expensePage");
        registry.addViewController("/saveExpense").setViewName("saveExpense");
        registry.addViewController("/updateExpense").setViewName("updateExpense");
        registry.addViewController("/expense/update").setViewName("updateExpense");
        registry.addViewController("/saveIncome").setViewName("saveIncome");
        registry.addViewController("/updateIncome").setViewName("updateIncome");
        registry.addViewController("/expense/Income").setViewName("updateIncome");
        registry.addViewController("/saveGoal").setViewName("saveGoal");
        registry.addViewController("/updateGoal").setViewName("updateGoal");
        registry.addViewController("/expense/Goal").setViewName("updateGoal");
        registry.addViewController("/moreGoalDetails").setViewName("moreGoalDetails");
    }

}
