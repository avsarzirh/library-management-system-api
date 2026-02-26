package com.avsarzirh.LibraryManagementSystemAPI.repository;

import com.avsarzirh.LibraryManagementSystemAPI.entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowerRepository extends JpaRepository<Borrower, Long> {
    boolean existsByEmailIgnoreCaseOrPhoneNumber(String email, String phoneNumber);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    List<Borrower> findAllByFirstNameContainsIgnoreCase(String firstName);
    List<Borrower> findAllByLastNameContainsIgnoreCase(String lastName);
    List<Borrower> findAllByEmailContainsIgnoreCase(String email);
    List<Borrower> findAllByPhoneNumberContainsIgnoreCase(String phoneNumber);

}
