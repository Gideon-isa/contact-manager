package com.gideon.contact_manager.infrastructure.persistence;

import com.gideon.contact_manager.domain.model.Contact;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class ContactSpecificationSearch {
    public static Specification<Contact> searchContacts(String searchTerm) {
        return (root, query, criteriaBuilder) -> {
            if (searchTerm == null || searchTerm.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            String likePattern = "%" + searchTerm.toLowerCase() + "%";

            Predicate phonePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("phoneNumber")), likePattern);
            Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), likePattern);
            Predicate lastNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), likePattern);
            Predicate emailPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), likePattern);
            Predicate addressPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("address")), likePattern);

            return criteriaBuilder.or(
                    phonePredicate,
                    namePredicate,
                    emailPredicate,
                    lastNamePredicate,
                    addressPredicate);
        };
    }
}
