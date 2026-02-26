package com.avsarzirh.LibraryManagementSystemAPI.service;

import com.avsarzirh.LibraryManagementSystemAPI.dto.BorrowerRequestDTO;
import com.avsarzirh.LibraryManagementSystemAPI.dto.BorrowerResponseDTO;
import com.avsarzirh.LibraryManagementSystemAPI.entity.Borrower;
import com.avsarzirh.LibraryManagementSystemAPI.exception.BookNotFoundException;
import com.avsarzirh.LibraryManagementSystemAPI.exception.BorrowerNotFoundException;
import com.avsarzirh.LibraryManagementSystemAPI.exception.UniquePropertyViolationException;
import com.avsarzirh.LibraryManagementSystemAPI.repository.BorrowerRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BorrowerService {
    private final BorrowerRepository borrowerRepository;

    public BorrowerResponseDTO saveBorrower(BorrowerRequestDTO dto) {
        if (borrowerRepository.existsByEmailIgnoreCaseOrPhoneNumber(dto.email(), dto.phoneNumber())) {
            throw new UniquePropertyViolationException("A borrower with this email address or phone number already exists.");
        }

        //! Mapping
        Borrower newBorrower = new Borrower();
        newBorrower.setFirstName(dto.firstName());
        newBorrower.setLastName(dto.lastName());
        newBorrower.setPhoneNumber(dto.phoneNumber());
        newBorrower.setEmail(dto.email());

        Borrower borrower = borrowerRepository.save(newBorrower);

        return mapBorrowerToBorrowerResponseDTO(borrower);
    }

    public Page<BorrowerResponseDTO> findAllBorrowers(int page, int size, String sortBy, Sort.Direction order) {
        Pageable pageable = PageRequest.of(page -1, size, order, sortBy);
        return borrowerRepository.findAll(pageable).map(this::mapBorrowerToBorrowerResponseDTO);
    }

    private BorrowerResponseDTO mapBorrowerToBorrowerResponseDTO(Borrower borrower) {
        return new BorrowerResponseDTO(borrower.getId(),
                borrower.getFirstName(),
                borrower.getLastName(),
                borrower.getPhoneNumber(),
                borrower.getEmail(),
                borrower.getRegistrationDate());
    }

    public BorrowerResponseDTO findBorrowerById(Long id) {
        return mapBorrowerToBorrowerResponseDTO(findById(id));
    }

    public Borrower findById(Long id) {
       return borrowerRepository.findById(id).orElseThrow(
                () -> new BorrowerNotFoundException("No borrower found with given ID: " + id));
    }

    public BorrowerResponseDTO updateBorrowerById(Long id, BorrowerRequestDTO dto) {
        Borrower existingBorrower = findById(id);

        //! Email ve phone number degismis mi? Degistiyse DB'de var mi?
        if (!existingBorrower.getEmail().equals(dto.email())
                && borrowerRepository.existsByEmail(dto.email())) {
            throw new UniquePropertyViolationException("Email already in use, try again.");
        }

        if (!existingBorrower.getPhoneNumber().equals(dto.phoneNumber())
                && borrowerRepository.existsByPhoneNumber(dto.phoneNumber())) {
            throw new UniquePropertyViolationException("Phone number already in use, try again.");
        }

        existingBorrower.setFirstName(dto.firstName());
        existingBorrower.setLastName(dto.lastName());
        existingBorrower.setPhoneNumber(dto.phoneNumber());
        existingBorrower.setEmail(dto.email());

        return mapBorrowerToBorrowerResponseDTO(borrowerRepository.save(existingBorrower));
    }

    public void deleteBorrowerById(Long id) {
        borrowerRepository.delete(findById(id));
    }

    public Set<BorrowerResponseDTO> searchBorrowers(String firstName, String lastName, String email, String phoneNumber) {
        Set<Borrower> borrowers = new HashSet<>();

        if (!firstName.isBlank()) {
            borrowers.addAll(borrowerRepository.findAllByFirstNameContainsIgnoreCase(firstName));
        }
        if (!lastName.isBlank()) {
            borrowers.addAll(borrowerRepository.findAllByLastNameContainsIgnoreCase(lastName));
        }
        if (!email.isBlank()) {
            borrowers.addAll(borrowerRepository.findAllByEmailContainsIgnoreCase(email));
        }
        if (!phoneNumber.isBlank()) {
            borrowers.addAll(borrowerRepository.findAllByPhoneNumberContainsIgnoreCase(phoneNumber));
        }

        if (firstName.isBlank() && lastName.isBlank() && email.isBlank() && phoneNumber.isBlank()) {
            return borrowerRepository.findAll()
                    .stream()
                    .map(this::mapBorrowerToBorrowerResponseDTO)
                    .collect(Collectors.toSet());
        }

        return borrowers.stream().map(this::mapBorrowerToBorrowerResponseDTO).collect(Collectors.toSet());
    }


}
