package com.vmoscalciuc.budget.config;


import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceProvider;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


//@Configuration
//@EnableTransactionManagement
//public class HibernateConfig {
//
////    @Bean
////    public LocalSessionFactoryBean sessionFactory() {
////        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
////        sessionFactory.setDataSource(dataSource());
////
////        sessionFactory.setPackagesToScan("com.vmoscalciuc.budget.model");
////        sessionFactory.setHibernateProperties(hibernateProperties());
////
////        return sessionFactory;
////    }
//
//    @Bean
//    public HikariDataSource dataSource() {
//        HikariDataSource dataSource = new HikariDataSource();
//        dataSource.setDriverClassName("org.postgresql.Driver");
//        dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/budget");
//        dataSource.setUsername("postgres");
//        dataSource.setPassword("0911529386Vm");
//        return dataSource;
//    }
//
//    @Bean
//    public PlatformTransactionManager hibernateTransactionManager() {
//        HibernateTransactionManager transactionManager
//                = new HibernateTransactionManager();
//        transactionManager.setSessionFactory(sessionFactory());
//        return transactionManager;
//    }
//
//    @Bean
//    public SessionFactory sessionFactory() {
//        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
//        MetadataSources metadataSources = new MetadataSources(registry);
//        metadataSources.addAnnotatedClass(com.vmoscalciuc.budget.model.User.class); // Add your entity class
//        metadataSources.addAnnotatedClass(com.vmoscalciuc.budget.model.Role.class); // Add your entity class
//        return metadataSources.buildMetadata().buildSessionFactory();
//    }
//    private final Properties hibernateProperties() {
//        Properties hibernateProperties = new Properties();
//        hibernateProperties.setProperty(
//                "hibernate.hbm2ddl.auto", "validate");
//        hibernateProperties.setProperty(
//                "hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
//        return hibernateProperties;
//    }
//
//    @Bean
//    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
//        return entityManagerFactory.createEntityManager();
//    }
//}

