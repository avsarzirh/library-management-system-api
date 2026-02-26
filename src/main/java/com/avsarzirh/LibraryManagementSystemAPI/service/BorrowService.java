package com.avsarzirh.LibraryManagementSystemAPI.service;

import com.avsarzirh.LibraryManagementSystemAPI.dto.BorrowRequestDTO;
import com.avsarzirh.LibraryManagementSystemAPI.entity.Book;
import com.avsarzirh.LibraryManagementSystemAPI.entity.Borrow;
import com.avsarzirh.LibraryManagementSystemAPI.entity.Borrower;
import com.avsarzirh.LibraryManagementSystemAPI.exception.BookAlreadyReturnedException;
import com.avsarzirh.LibraryManagementSystemAPI.exception.BookUnavailableException;
import com.avsarzirh.LibraryManagementSystemAPI.repository.BorrowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BorrowService {
    private final BorrowRepository borrowRepository;
    private final BookService bookService;
    private final BorrowerService borrowerService;

    public Borrow borrowBook(BorrowRequestDTO dto) {
        Book foundBook = bookService.findBookById(dto.bookId());
        Borrower foundBorrower = borrowerService.findById(dto.borrowerId());

        //!!! Belirtilen kitap su an odunc alinabilir mi?
        Borrow borrow = borrowRepository.findFirstByBookOrderByIdDesc(foundBook);

        if (borrow != null && borrow.getReturnedAt() == null) {
            //! bu durumda kitap su anda baskasindadir.
            throw new BookUnavailableException("This book is currently borrowed by someone else.");
        }
        //!!! eger if'e girilmezse kitap ya hic alinmamistir, ya da son alan kisi teslim etmistir.

        Borrow newBorrow = new Borrow();
        newBorrow.setBook(foundBook);
        newBorrow.setBorrower(foundBorrower);

        newBorrow.setBorrowedAt(LocalDateTime.now());
        newBorrow.setExpectedReturnAt(LocalDateTime.now().plusDays(7));

        return borrowRepository.save(newBorrow);
    }

    public Map<String, ?> returnBook(Long bookId) {
        Book foundBook = bookService.findBookById(bookId);

        Borrow borrow = borrowRepository.findFirstByBookOrderByIdDesc(foundBook);

        //! Kitap hic odunc alinmadiysa
        //! Alindiysa da halihazirda teslim edildiyse
        if (borrow == null || borrow.getReturnedAt() != null) {
            throw new BookAlreadyReturnedException("This book is already in the library.");
        }

        borrow.setReturnedAt(LocalDateTime.now());

        borrowRepository.save(borrow);

        return Map.of("message", "Book returned to the library successfully.");
    }
}

