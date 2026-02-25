package com.avsarzirh.LibraryManagementSystemAPI.controller;

import com.avsarzirh.LibraryManagementSystemAPI.dto.BookRequestDTO;
import com.avsarzirh.LibraryManagementSystemAPI.entity.Book;
import com.avsarzirh.LibraryManagementSystemAPI.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController //Controller ve ResponseBody anotasyonlarının birlşimidir.
@RequestMapping("/book") //Bu anotasyon, sınıfın hangi URL yoluna hizmet vereceğini belirtir.
@RequiredArgsConstructor //Dependency Injection için
public class BookController {
    private final BookService bookService;

    @PostMapping //http://localhost:8082/book
    public ResponseEntity<Book> saveBook(@Valid @RequestBody BookRequestDTO dto) {
        return new ResponseEntity<>(bookService.save(dto) , HttpStatus.CREATED);
    }

    @GetMapping //http://localhost:8082/book
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.findAllBooks());
    }

    @GetMapping("/{id}") //http://localhost:8080/book/ID + GET
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findBookById(id));
    }

    @DeleteMapping("/{id}") //http://localhost:8080/book/ID + DELETE
    public ResponseEntity<Void> deleteBookById(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return ResponseEntity.noContent().build();
    }

    //! Search - filtering
    @GetMapping("/search")//http://localhost:8080/book/search
    public ResponseEntity<Set<Book>> searchBooks(@RequestParam(required = false, defaultValue = "") String title,
                                                 @RequestParam(required = false, defaultValue = "") String author,
                                                 @RequestParam(required = false, defaultValue = "") String publishYear) {
        return ResponseEntity.ok(bookService.searchBooks(title, author, publishYear));
    }
/*
    @GetMapping("/s") //http://localhost:8080/book/s?page=0&size=25page=0&size=25&sort=title,asc&sort=title,asc
    public ResponseEntity<Page<Book>> getAllBooksUsingPagination(Pageable pageable) {

    }
*/

    @GetMapping("/s")
    public ResponseEntity<Page<Book>> getAllBooksUsingPagination(@RequestParam int page,
                                                                 @RequestParam(required = false, defaultValue = "25") int size,
                                                                 @RequestParam(required = false, defaultValue = "id") String sortBy,
                                                                 @RequestParam(required = false, defaultValue = "ASC") Sort.Direction order) {

        return ResponseEntity.ok(bookService.findAllWithPagination(page, size, sortBy, order));
    }

    @PutMapping("/{id}") //http://localhost:8080/book/1 + PUT
    public ResponseEntity<Book> updateBookById(@PathVariable Long id, @Valid @RequestBody BookRequestDTO dto) {
        return ResponseEntity.ok(bookService.updateBook(id, dto));
    }





}
