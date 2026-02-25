package com.avsarzirh.LibraryManagementSystemAPI.controller;

import com.avsarzirh.LibraryManagementSystemAPI.dto.BookCreateRequestDTO;
import com.avsarzirh.LibraryManagementSystemAPI.entity.Book;
import com.avsarzirh.LibraryManagementSystemAPI.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //Controller ve ResponseBody anotasyonlarının birlşimidir.
@RequestMapping("/book") //Bu anotasyon, sınıfın hangi URL yoluna hizmet vereceğini belirtir.
@RequiredArgsConstructor //Dependency Injection için
public class BookController {
    private final BookService bookService;

    @PostMapping //http://localhost:8082/book
    public ResponseEntity<Book> saveBook(@Valid @RequestBody BookCreateRequestDTO dto) {
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


}
