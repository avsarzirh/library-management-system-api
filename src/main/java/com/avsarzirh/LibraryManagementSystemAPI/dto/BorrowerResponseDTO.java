package com.avsarzirh.LibraryManagementSystemAPI.dto;

import java.time.LocalDate;

public record BorrowerResponseDTO(
        Long id,
        String firstName,
        String lastName,
        String phoneNumber,
        String email,
        LocalDate registrationDate
) {
}
