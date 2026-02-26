package com.avsarzirh.LibraryManagementSystemAPI.controller;

import com.avsarzirh.LibraryManagementSystemAPI.dto.BorrowerRequestDTO;
import com.avsarzirh.LibraryManagementSystemAPI.dto.BorrowerResponseDTO;
import com.avsarzirh.LibraryManagementSystemAPI.service.BorrowerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController //Controller ve ResponseBody anotasyonlarının birlşimidir.
@RequestMapping("/borrower") //Bu anotasyon, sınıfın hangi URL yoluna hizmet vereceğini belirtir.
@RequiredArgsConstructor //Dependency Injection için
public class BorrowerController {
    private final BorrowerService borrowerService;

    @PostMapping
    public ResponseEntity<BorrowerResponseDTO> saveBorrower(@RequestBody @Valid BorrowerRequestDTO dto) {
        return new ResponseEntity<>(borrowerService.saveBorrower(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<BorrowerResponseDTO>> getAllBorrowers(@RequestParam int page,
                                                                     @RequestParam(required = false, defaultValue = "25") int size,
                                                                     @RequestParam(required = false, defaultValue = "id") String sortBy,
                                                                     @RequestParam(required = false, defaultValue = "ASC") Sort.Direction order) {
        return ResponseEntity.ok(borrowerService.findAllBorrowers(page, size, sortBy, order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BorrowerResponseDTO> getBorrowerById(@PathVariable Long id) {

        return ResponseEntity.ok(borrowerService.findBorrowerById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBorrowerById(@PathVariable Long id) {
        borrowerService.deleteBorrowerById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Set<BorrowerResponseDTO>> searchBorrowers(@RequestParam(required = false, defaultValue = "") String firstName,
                                                                    @RequestParam(required = false, defaultValue = "") String lastName,
                                                                    @RequestParam(required = false, defaultValue = "") String phoneNumber,
                                                                    @RequestParam(required = false, defaultValue = "") String email) {
        return ResponseEntity.ok(borrowerService.searchBorrowers(firstName, lastName, email, phoneNumber));
    }















}
