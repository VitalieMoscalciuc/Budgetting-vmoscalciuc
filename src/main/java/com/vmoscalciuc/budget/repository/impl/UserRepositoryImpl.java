package com.vmoscalciuc.budget.repository.impl;

import com.vmoscalciuc.budget.model.User;
import com.vmoscalciuc.budget.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private final EntityManager entityManager;

//    public UserRepositoryImpl(EntityManager entityManager) {
//        this.entityManager = entityManager;
//    }

    @Override
    public User save(User u) {
        System.out.println("Saving User");
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager.persist(u);

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
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            User user = entityManager.find(User.class, userId);
            System.out.println("balance "+user.getBalance()+" - updated amount "+updatedAmount);
            user.setBalance(user.getBalance() - updatedAmount);
            entityManager.merge(user);

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
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            User user = entityManager.find(User.class, userId);
            user.setBalance(user.getBalance() + updatedAmount);
            entityManager.merge(user);

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
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.id = :userId", User.class);
        query.setParameter("userId", userId);
        return query.getSingleResult();
    }


    @Override
    public Optional<User> findByEmail(String email) {
        System.out.println("finding by email from userRepositoryImpl");
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

}
