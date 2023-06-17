package com.vmoscalciuc.budget.repository;

import com.vmoscalciuc.budget.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UserRepository {
    User save(User u);

    void updateUserBalance(Long userId,Double updatedAmount);
    void addToUserBalance(Long userId,Double updatedAmount);

    User findById(Long userId);

   Optional<User> findByEmail(String email);
}
