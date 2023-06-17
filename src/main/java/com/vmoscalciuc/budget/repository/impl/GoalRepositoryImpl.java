package com.vmoscalciuc.budget.repository.impl;

import com.vmoscalciuc.budget.model.Goal;
import com.vmoscalciuc.budget.model.Goal;
import com.vmoscalciuc.budget.repository.GoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GoalRepositoryImpl implements GoalRepository {
    @PersistenceContext
    private final EntityManager entityManager;
    @Override
    public Goal save(Goal goal) {
        System.out.println("Saving goal");
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager.persist(goal);

            transaction.commit();
            return goal;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public Goal findById(Long goalId) {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Goal goal = entityManager.find(Goal.class, goalId);
            System.out.println("goal id in repoimpl = "+goal.getId());
            transaction.commit();
            return goal;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public List<Goal> findAll() {
        System.out.println("finding all goals");
        TypedQuery<Goal> query = entityManager.createQuery(
                "SELECT e FROM Goal e", Goal.class);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            throw new RuntimeException("No goals found");
        }
    }

    @Override
    public Goal update(Goal newGoal) {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager.merge(newGoal);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
        return null;
    }

    @Override
    public void delete(Long goalId) {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Goal goal = entityManager.find(Goal.class, goalId);
            entityManager.remove(goal);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public void updateGoalInvestment(Long goalId,Double amount) {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Goal goal = entityManager.find(Goal.class, goalId);
            goal.setInvestment(goal.getInvestment()+amount);
            entityManager.merge(goal);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
