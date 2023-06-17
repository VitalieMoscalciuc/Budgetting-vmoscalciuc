package com.vmoscalciuc.budget.repository.impl;

import com.vmoscalciuc.budget.model.Expense;
import com.vmoscalciuc.budget.model.User;
import com.vmoscalciuc.budget.repository.ExpenseRepository;
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
public class ExpenseRepositoryImpl implements ExpenseRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Expense save(Expense expense) {
        System.out.println("Saving expense");
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager.persist(expense);

            transaction.commit();
            return expense;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public Expense findById(Long expenseId) {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Expense expense = entityManager.find(Expense.class, expenseId);
            System.out.println("expense id in repoimpl = "+expense.getId());
            transaction.commit();
            return expense;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public List<Expense> findAll() {
        System.out.println("finding all expenses");
        TypedQuery<Expense> query = entityManager.createQuery(
                "SELECT e FROM Expense e", Expense.class);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            throw new RuntimeException("No expenses found");
        }
    }

    @Override
    public Expense update(Expense newExpense) {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager.merge(newExpense);

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
    public void delete(Long expenseId) {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Expense expense = entityManager.find(Expense.class, expenseId);
            entityManager.remove(expense);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

}
