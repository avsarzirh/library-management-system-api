package com.avsarzirh.LibraryManagementSystemAPI.service;

import com.avsarzirh.LibraryManagementSystemAPI.dto.BookRequestDTO;
import com.avsarzirh.LibraryManagementSystemAPI.entity.Book;
import com.avsarzirh.LibraryManagementSystemAPI.exception.BookNotFoundException;
import com.avsarzirh.LibraryManagementSystemAPI.repository.BookRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public Book save(BookRequestDTO dto) {
        Book newBook = new Book();

        newBook.setTitle(dto.title());
        newBook.setAuthor(dto.author());
        newBook.setPublishYear(dto.publishYear());

        return bookRepository.save(newBook);
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Book findBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(
                () -> new BookNotFoundException("No book found with given ID: " + id)
        );
    }

    public void deleteBookById(Long id) {
        Book book = findBookById(id);
        //bookRepository.deleteById(id);
        bookRepository.delete(book);
    }

    public Set<Book> searchBooks(String title, String author, String publishYear) {
        Set<Book> resultSet = new HashSet<>();

        if (!title.isBlank()) {
            resultSet.addAll(bookRepository.findAllByTitleContainsIgnoreCase(title));
        }

        if (!author.isBlank()) {
            resultSet.addAll(bookRepository.findAllByAuthorContainsIgnoreCase(author));
        }

        if (!publishYear.isBlank()) {
            resultSet.addAll(bookRepository.findAllByPublishYearContainsIgnoreCase(publishYear));
        }

        //! Yukaridaki 3 if, 1 tane parametrenin gelmesi, 2 tane gelmesi, ya da ucunun de gelmesi durumlarinda
        //! Istenilen ciktiyi verecektir. Tek kalan senaryo, uc parametrenin de verilmedigi senaryodur.
        if (title.isBlank() && author.isBlank() && publishYear.isBlank()) {
            return new HashSet<>(findAllBooks());
        }

        return resultSet;
    }

    public Page<Book> findAllWithPagination(int page, int size, String sortBy, Sort.Direction order) {

        Pageable pageable = PageRequest.of(page - 1, size, order, sortBy);
        return bookRepository.findAll(pageable);
    }

    public Book updateBook(Long id, BookRequestDTO dto) {
        Book existingBook = findBookById(id);

        existingBook.setTitle(dto.title());
        existingBook.setAuthor(dto.author());
        existingBook.setPublishYear(dto.publishYear());

        return bookRepository.save(existingBook);
    }
}
