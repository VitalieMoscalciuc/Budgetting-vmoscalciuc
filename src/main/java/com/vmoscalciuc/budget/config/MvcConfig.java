package com.vmoscalciuc.budget.config;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.*;

@EnableWebMvc
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    private ApplicationContext applicationContext;

@Override
public void addResourceHandlers(ResourceHandlerRegistry registry) {
    if (!registry.hasMappingForPattern("/assets/**")) {
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/assets/");
    }
}

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/mainPage").setViewName("/mainPage");
        registry.addViewController("/login").setViewName("/login");
        registry.addViewController("/register").setViewName("/register");
        registry.addViewController("/expensePage").setViewName("expensePage");
        registry.addViewController("/saveExpense").setViewName("/saveExpense");
        registry.addViewController("/updateExpense").setViewName("/updateExpense");
        registry.addViewController("/expense/update").setViewName("/updateExpense");
        registry.addViewController("/saveIncome").setViewName("/saveIncome");
        registry.addViewController("/updateIncome").setViewName("/updateIncome");
        registry.addViewController("/expense/Income").setViewName("/updateIncome");
        registry.addViewController("/saveGoal").setViewName("/saveGoal");
        registry.addViewController("/updateGoal").setViewName("/updateGoal");
        registry.addViewController("/expense/Goal").setViewName("/updateGoal");
        registry.addViewController("/moreGoalDetails").setViewName("/moreGoalDetails");
    }

}
