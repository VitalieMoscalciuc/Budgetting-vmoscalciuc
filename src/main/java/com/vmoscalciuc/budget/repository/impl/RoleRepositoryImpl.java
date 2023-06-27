package com.vmoscalciuc.budget.repository.impl;

import com.vmoscalciuc.budget.model.Role;
import com.vmoscalciuc.budget.model.User;
import com.vmoscalciuc.budget.repository.RoleRepository;
import com.vmoscalciuc.budget.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Optional;



@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

    @Autowired
    private final SessionFactory sessionFactory;

    Session session = null;
    Transaction transaction = null;


//

    @Override
    public Optional<Role> findByName(String name) {
        Session session = sessionFactory.openSession();
        TypedQuery<Role> query = session.createQuery(
                "SELECT r FROM Role r WHERE r.name = :name", Role.class);
        query.setParameter("name", name);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}