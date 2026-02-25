package com.avsarzirh.LibraryManagementSystemAPI.repository;

import com.avsarzirh.LibraryManagementSystemAPI.entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowerRepository extends JpaRepository<Borrower, Long> {
}
