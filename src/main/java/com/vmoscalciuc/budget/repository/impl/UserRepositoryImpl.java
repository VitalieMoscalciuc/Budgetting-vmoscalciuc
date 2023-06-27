package com.vmoscalciuc.budget.repository.impl;

import com.vmoscalciuc.budget.model.*;
import com.vmoscalciuc.budget.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private final SessionFactory sessionFactory;

    Session session = null;
    Transaction transaction = null;

    @Override
    public User save(User u) {
        System.out.println("Saving User");
        EntityTransaction transaction = null;
        try {
            Role role = new Role();
            role.setId(1L);
            role.setName("USER");
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.merge(role);
            session.persist(u);

            transaction.commit();
            return u;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public void updateUserBalance(Long userId, Double updatedAmount) {
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            User user = session.find(User.class, userId);
            System.out.println("balance "+user.getBalance()+" - updated amount "+updatedAmount);
            user.setBalance(user.getBalance() - updatedAmount);
            session.merge(user);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public void addToUserBalance(Long userId, Double updatedAmount) {
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            User user = session.find(User.class, userId);
            user.setBalance(user.getBalance() + updatedAmount);
            session.merge(user);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }


    @Override
    public User findById(Long userId) {
        System.out.println("finding by id");
        Session session = sessionFactory.openSession();
        TypedQuery<User> query = session.createQuery(
                "SELECT u FROM User u WHERE u.id = :userId", User.class);
        query.setParameter("userId", userId);
        return query.getSingleResult();
    }


    @Override
    public Optional<User> findByEmail(String email) {
        System.out.println("finding by email from userRepositoryImpl");
        Session session = sessionFactory.openSession(); // Obtain the current session from SessionFactory
        TypedQuery<User> query = session.createQuery(
                "SELECT u FROM User u WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }


}
