package com.avsarzirh.LibraryManagementSystemAPI.controller;

import com.avsarzirh.LibraryManagementSystemAPI.dto.BorrowRequestDTO;
import com.avsarzirh.LibraryManagementSystemAPI.entity.Borrow;
import com.avsarzirh.LibraryManagementSystemAPI.service.BorrowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/borrow")
@RequiredArgsConstructor
public class BorrowController {
    private final BorrowService borrowService;

    @PostMapping
    public ResponseEntity<Borrow> borrowBook(@RequestBody @Valid BorrowRequestDTO dto) {
        return new ResponseEntity<>(borrowService.borrowBook(dto), HttpStatus.CREATED);
    }

    @PatchMapping("/return/{bookId}")
    public ResponseEntity<Map<String, ?>> returnBook(@PathVariable Long bookId) {
        return ResponseEntity.ok(borrowService.returnBook(bookId));
    }
}
