package com.vmoscalciuc.budget.repository.impl;

import com.vmoscalciuc.budget.model.Goal;
import com.vmoscalciuc.budget.model.Goal;
import com.vmoscalciuc.budget.repository.GoalRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GoalRepositoryImpl implements GoalRepository {

    @Autowired
    private final SessionFactory sessionFactory;

    Session session = null;
    Transaction transaction = null;

    @Override
    public Goal save(Goal goal) {
        System.out.println("Saving goal");
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            session.persist(goal);

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
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Goal goal = session.find(Goal.class, goalId);
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
    public List<Goal> findAll(Long userId) {
        Session session = sessionFactory.openSession();
        System.out.println("finding all goals");
        TypedQuery<Goal> query = session.createQuery(
                "SELECT e FROM Goal e WHERE e.user.id = :userId", Goal.class);
        query.setParameter("userId", userId);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            throw new RuntimeException("No goals found");
        }
    }

    @Override
    public Goal update(Goal newGoal) {
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            session.merge(newGoal);

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
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Goal goal = session.find(Goal.class, goalId);
            session.remove(goal);

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
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Goal goal = session.find(Goal.class, goalId);
            goal.setInvestment(goal.getInvestment()+amount);
            session.merge(goal);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
