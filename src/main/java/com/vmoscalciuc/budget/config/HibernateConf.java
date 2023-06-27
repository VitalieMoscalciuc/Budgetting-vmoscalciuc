package com.vmoscalciuc.budget.config;

import com.vmoscalciuc.budget.repository.RoleRepository;
import com.vmoscalciuc.budget.repository.UserRepository;
import com.vmoscalciuc.budget.repository.impl.RoleRepositoryImpl;
import com.vmoscalciuc.budget.repository.impl.UserRepositoryImpl;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Component
public class HibernateConf {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml").build();
            Metadata metadata = new MetadataSources(standardRegistry).getMetadataBuilder().build();
            return metadata.getSessionFactoryBuilder().build();

        } catch (HibernateException he) {
            System.out.println("Session Factory creation failure");
            throw he;
        }
    }
    @Bean
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
