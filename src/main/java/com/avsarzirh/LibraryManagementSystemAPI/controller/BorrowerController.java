package com.avsarzirh.LibraryManagementSystemAPI.controller;

import com.avsarzirh.LibraryManagementSystemAPI.service.BorrowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //Controller ve ResponseBody anotasyonlarının birlşimidir.
@RequestMapping("/borrower") //Bu anotasyon, sınıfın hangi URL yoluna hizmet vereceğini belirtir.
@RequiredArgsConstructor //Dependency Injection için
public class BorrowerController {
    private final BorrowerService borrowerService;
}
