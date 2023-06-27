package com.vmoscalciuc.budget.repository.impl;

import com.vmoscalciuc.budget.model.Expense;
import com.vmoscalciuc.budget.repository.ExpenseRepository;
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
public class ExpenseRepositoryImpl implements ExpenseRepository {

    @Autowired
    private final SessionFactory sessionFactory;

    Session session = null;
    Transaction transaction = null;

    @Override
    public Expense save(Expense expense) {
        System.out.println("Saving expense");

        try {
            session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            session.persist(expense);

            transaction.commit();
            return expense;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }


    @Override
    public Expense findById(Long expenseId) {
        EntityTransaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Expense expense = session.find(Expense.class, expenseId);
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
    public List<Expense> findAll(Long userId) {
        Session session = sessionFactory.openSession();
        System.out.println("finding all expenses");
        TypedQuery<Expense> query = session.createQuery(
                "SELECT e FROM Expense e WHERE e.user.id = :userId", Expense.class);
        query.setParameter("userId", userId);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            throw new RuntimeException("No expenses found");
        }
    }

    @Override
    public Expense update(Expense newExpense) {
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            session.merge(newExpense);

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
        try {
            transaction = session.getTransaction();
            transaction.begin();

            Expense expense = session.find(Expense.class, expenseId);
            session.remove(expense);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

}
