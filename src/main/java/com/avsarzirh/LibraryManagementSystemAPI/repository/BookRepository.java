package com.avsarzirh.LibraryManagementSystemAPI.repository;

import com.avsarzirh.LibraryManagementSystemAPI.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByTitleContainsIgnoreCase(String title);
    List<Book> findAllByAuthorContainsIgnoreCase(String author);
    List<Book> findAllByPublishYearContainsIgnoreCase(String publishYear);

    //TASK 9 için yapıldı ancak searh metodu bu işi zaten yapıyor.
    @Query("FROM Book WHERE author LIKE ?1")
    List<Book> findAllBooksOfAuthor(String author);
}
