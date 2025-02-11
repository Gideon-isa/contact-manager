package com.gideon.contact_manager.infrastructure.persistence;

import com.gideon.contact_manager.domain.model.Contact;
import com.gideon.contact_manager.domain.repository.ContactRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaContactRepository extends JpaRepository<Contact, Long>, JpaSpecificationExecutor<Contact> {

}
