package com.gideon.contact_manager.infrastructure.persistence;

import com.gideon.contact_manager.domain.model.User;
import com.gideon.contact_manager.domain.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public interface JpaUserRepository extends JpaRepository<User, Long>, UserRepository {

}
