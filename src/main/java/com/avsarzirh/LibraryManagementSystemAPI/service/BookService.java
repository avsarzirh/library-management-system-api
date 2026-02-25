package com.avsarzirh.LibraryManagementSystemAPI.service;

import com.avsarzirh.LibraryManagementSystemAPI.dto.BookCreateRequestDTO;
import com.avsarzirh.LibraryManagementSystemAPI.entity.Book;
import com.avsarzirh.LibraryManagementSystemAPI.exception.BookNotFoundException;
import com.avsarzirh.LibraryManagementSystemAPI.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public Book save(BookCreateRequestDTO dto) {
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



}
