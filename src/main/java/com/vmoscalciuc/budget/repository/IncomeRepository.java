package com.vmoscalciuc.budget.repository;

import com.vmoscalciuc.budget.model.Income;

import java.util.List;

public interface IncomeRepository {
    Income save(Income in);

    Income findById(Long incomeId);

    List<Income> findAll();

    Income update(Income newIncome);

    void delete(Long incomeId);
}
