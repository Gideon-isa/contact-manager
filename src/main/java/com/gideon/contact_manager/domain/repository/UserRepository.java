package com.gideon.contact_manager.domain.repository;

import com.gideon.contact_manager.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long id);
    List<User> findAll();
    void deleteById(Long id);
}
