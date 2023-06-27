package com.vmoscalciuc.budget.repository.impl;

import com.vmoscalciuc.budget.model.Income;
import com.vmoscalciuc.budget.repository.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class IncomeRepositoryImpl implements IncomeRepository {

    @Autowired
    private final SessionFactory sessionFactory;

    Session session = null;
    Transaction transaction = null;


    @Override
    public Income save(Income income) {
        System.out.println("Saving income");
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            session.persist(income);

            transaction.commit();
            return income;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public Income findById(Long incomeId) {
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Income income = session.find(Income.class, incomeId);
            transaction.commit();
            return income;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public List<Income> findAll(Long userId) {
        Session session = sessionFactory.openSession();
        System.out.println("finding all incomes");
        TypedQuery<Income> query = session.createQuery(
                "SELECT i FROM Income i where i.user.id = :userId", Income.class);
        query.setParameter("userId", userId);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            throw new RuntimeException("No expenses found");
        }
    }

    @Override
    public Income update(Income newIncome) {
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            session.merge(newIncome);

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
    public void delete(Long incomeId) {
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Income income = session.find(Income.class, incomeId);
            session.remove(income);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
