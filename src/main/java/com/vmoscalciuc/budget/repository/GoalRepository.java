package com.vmoscalciuc.budget.repository;

import com.vmoscalciuc.budget.model.Goal;
import com.vmoscalciuc.budget.model.Goal;

import java.util.List;

public interface GoalRepository {

    Goal save(Goal goal);

    Goal findById(Long goalId);

    List<Goal> findAll(Long userId);

    Goal update(Goal newGoal);

    void delete(Long goalId);

    void updateGoalInvestment(Long goalId, Double amount);
}
