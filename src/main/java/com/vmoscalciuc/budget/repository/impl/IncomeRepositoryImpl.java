package com.vmoscalciuc.budget.repository.impl;

import com.vmoscalciuc.budget.model.Expense;
import com.vmoscalciuc.budget.model.Income;
import com.vmoscalciuc.budget.repository.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class IncomeRepositoryImpl implements IncomeRepository {

    @PersistenceContext
    private final EntityManager entityManager;
    @Override
    public Income save(Income income) {
        System.out.println("Saving income");
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager.persist(income);

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
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Income income = entityManager.find(Income.class, incomeId);
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
    public List<Income> findAll() {
        System.out.println("finding all incomes");
        TypedQuery<Income> query = entityManager.createQuery(
                "SELECT i FROM Income i", Income.class);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            throw new RuntimeException("No expenses found");
        }
    }

    @Override
    public Income update(Income newIncome) {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager.merge(newIncome);

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
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Income income = entityManager.find(Income.class, incomeId);
            entityManager.remove(income);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
