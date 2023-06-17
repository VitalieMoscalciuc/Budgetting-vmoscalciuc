package com.vmoscalciuc.budget.repository.impl;

import com.vmoscalciuc.budget.model.Role;
import com.vmoscalciuc.budget.model.User;
import com.vmoscalciuc.budget.repository.RoleRepository;
import com.vmoscalciuc.budget.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Optional;



@Repository
public class RoleRepositoryImpl implements RoleRepository {

    private final EntityManager entityManager;

    public RoleRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

//    @Override
//    public User save(User u) {
//        entityManager.persist(u);
//        return u;
//    }
//

    @Override
    public Optional<Role> findByName(String name) {
        TypedQuery<Role> query = entityManager.createQuery(
                "SELECT r FROM Role r WHERE r.name = :name", Role.class);
        query.setParameter("name", name);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}