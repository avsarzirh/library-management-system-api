package com.avsarzirh.LibraryManagementSystemAPI.repository;

import com.avsarzirh.LibraryManagementSystemAPI.entity.Book;
import com.avsarzirh.LibraryManagementSystemAPI.entity.Borrow;
import com.avsarzirh.LibraryManagementSystemAPI.entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowRepository extends JpaRepository<Borrow, Long> {
    Borrow findFirstByBookOrderByIdDesc(Book book);
}
