package com.avsarzirh.LibraryManagementSystemAPI.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record BorrowerRequestDTO(
        @NotBlank(message = "First name cannot be empty")
        String firstName,
        @NotBlank(message = "Last name cannot be empty")
        String lastName,
        @NotBlank(message = "Phone number cannot be empty")
        String phoneNumber,
        @NotBlank(message = "Email cannot be empty")
        @Email
        String email
) {
}
