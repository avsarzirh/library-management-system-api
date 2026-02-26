package com.avsarzirh.LibraryManagementSystemAPI.dto;

public record BorrowRequestDTO(
        Long bookId,
        Long borrowerId,
        Integer expectedReturnInDays
) { }
