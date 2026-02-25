package com.avsarzirh.LibraryManagementSystemAPI.dto;

import jakarta.validation.constraints.NotBlank;

public record BookCreateRequestDTO(
        @NotBlank(message = "Book title cannot be empty.")
        String title,

        @NotBlank(message = "Book author cannot be empty.")
        String author,

        @NotBlank(message = "Book publish year cannot be empty.")
        String publishYear
) { }

