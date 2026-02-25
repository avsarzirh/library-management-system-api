package com.avsarzirh.LibraryManagementSystemAPI.service;

import com.avsarzirh.LibraryManagementSystemAPI.repository.BorrowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BorrowService {

    private final BorrowRepository borrowRepository;

}
