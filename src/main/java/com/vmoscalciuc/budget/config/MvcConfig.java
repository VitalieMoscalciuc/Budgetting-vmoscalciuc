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
        registry.addViewController("/index");
        registry.addViewController("/mainPage");
        registry.addViewController("/login");
        registry.addViewController("/register");
        registry.addViewController("/expensePage");
        registry.addViewController("/saveExpense");
        registry.addViewController("/updateExpense");
        registry.addViewController("/expense/update");
        registry.addViewController("/saveIncome");
        registry.addViewController("/updateIncome");
        registry.addViewController("/expense/Income");
        registry.addViewController("/saveGoal");
        registry.addViewController("/updateGoal");
        registry.addViewController("/expense/Goal");
        registry.addViewController("/moreGoalDetails");
    }

}
