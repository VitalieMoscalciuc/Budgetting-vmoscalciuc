package com.vmoscalciuc.budget.config;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.*;
import org.thymeleaf.spring6.*;
import org.thymeleaf.spring6.view.*;
import org.thymeleaf.templatemode.*;
import org.thymeleaf.templateresolver.*;

@EnableWebMvc
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setCharacterEncoding("UTF-8");
        registry.viewResolver(viewResolver);
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setEnableSpringELCompiler(true);
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }

    private ITemplateResolver templateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

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
