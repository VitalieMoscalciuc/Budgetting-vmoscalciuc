package com.vmoscalciuc.budget.repository;

import com.vmoscalciuc.budget.model.Expense;

import java.util.List;

public interface ExpenseRepository {

        Expense save(Expense ex);

        Expense findById(Long expenseId);

        List<Expense> findAll();

        Expense update(Expense newExpense);

        void delete(Long expenseId);
}
