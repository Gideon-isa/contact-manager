package com.gideon.contact_manager.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface Contact {
    Contact save(Contact contact);
    Optional<Contact> findById(Long id);
    List<Contact> findAll();
    void deleteById(Long id);
}
